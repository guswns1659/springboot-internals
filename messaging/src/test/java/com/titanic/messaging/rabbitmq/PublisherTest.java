package com.titanic.messaging.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@EmbeddedKafka(partitions = 3)
@SpringBootTest
@ActiveProfiles("test")
public class PublisherTest {

    @Autowired
    private OldJackPublisher oldJackPublisher;

    @Autowired
    private NewJackPublisher newJackPublisher;

    @Autowired
    private RabbitAdmin oldRabbitAdmin;

    @Autowired
    @Qualifier("newRabbitAdmin")
    private RabbitAdmin newRabbitAdmin;

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Value("${spring.rabbitmq.old.listener}")
    private String oldListener;

    @Value("${spring.rabbitmq.new.listener}")
    private String newListener;


    @BeforeEach
    void setUp() {
        oldRabbitAdmin.purgeQueue("items", true);
        newRabbitAdmin.purgeQueue("items", true);
    }

    @AfterEach
    void tearDown() {
        oldRabbitAdmin.purgeQueue("items", true);
        newRabbitAdmin.purgeQueue("items", true);
        rabbitListenerEndpointRegistry.stop();
    }

    @Test
    void 구래빗큐에_pub_sub_성공테스트() throws JsonProcessingException {
        // given
        Data item = Data.builder().name("item").build();

        //when
        oldJackPublisher.publishMessages(item);
        rabbitListenerEndpointRegistry.getListenerContainer(
                oldListener
        ).start();
    }

    @Test
    void 새노드바라보는_컨슈머추가하고_새노드로_pub하도록_변경하고_기존컨슈머_정상동작테스트() throws JsonProcessingException {
        // given
        Data item2 = Data.builder().name("item2").build();

        // when
        newJackPublisher.publishMessage(item2); // 새노드로 publish
        oldJackPublisher.publishMessages(item2);
        rabbitListenerEndpointRegistry.getListenerContainer(
                "jackListener2"
        ).start();
        rabbitListenerEndpointRegistry.getListenerContainer(
                "jackListener"
        ).start();

        // then - listen됐다는 것을 verify하면 될 거 같은데 현재 listener는 다른 상태를 가지고 있지 않아서 생략

    }

    @Test
    void 구래빗에서_신래빗으로_메시지_프록시_정상동작() throws JsonProcessingException, InterruptedException {
        // given
        Data item2 = Data.builder().name("item2").build();

        // when
        oldJackPublisher.publishMessages(item2);

        rabbitListenerEndpointRegistry.getListenerContainer(
                newListener
        ).start();

        Thread.sleep(2000); // consume 되기 전에 앱이 종료되서 sleep 추가

        // then
    }
}
