package com.springboot.springbootinternals.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.core.BrokerAddress;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@EmbeddedKafka
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class JackProducerTest {

    private static final String TOPIC = "jack";
    BlockingQueue<ConsumerRecord<String, String>> records;
    KafkaMessageListenerContainer<String, String> container;
    private ProducerFactory<String, String> jackProducerFactory;
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, String> jackKafkaTemplate;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Value("${spring.embedded.kafka.brokers}")
    private String brokerAddresses;

    @BeforeEach
    void setUP() {
        embeddedKafkaBroker.addTopics("jack123");

        Map<String, Object> configs = new HashMap<>(
            KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
            configs, new StringDeserializer(), new StringDeserializer());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());

        Map<String, Object> pConfigs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        jackProducerFactory = new DefaultKafkaProducerFactory<>(pConfigs, new StringSerializer(),
            new StringSerializer());
        jackKafkaTemplate = new KafkaTemplate<>(jackProducerFactory);
    }

    @AfterEach
    void tearDown() {
        container.stop();
    }

    @Test
    @DisplayName("JackProducer produce success test")
    public void success_test() throws ExecutionException, InterruptedException {
        // given
        System.out.println(embeddedKafkaBroker.getTopics());
        BrokerAddress[] brokerAddresses = embeddedKafkaBroker.getBrokerAddresses();
        System.out.println(">>>>>>>>>>>>>" + Arrays.toString(brokerAddresses));

        // when
        ListenableFuture<SendResult<String, String>> future = jackKafkaTemplate.send("jack123", "key", "data");
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {

            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println(">>>>> success");

            }
        });

        // then
//        assertThat(stringStringSendResult.getRecordMetadata().offset()).isEqualTo(0);
    }
}
