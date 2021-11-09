package com.springboot.springbootinternals.rabbitmq;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@EmbeddedKafka(partitions = 3)
@SpringBootTest
@ActiveProfiles("test")
public class PublisherTest {

    @Autowired
    private JackPublisher jackPublisher;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue("items", true);
    }

    @AfterEach
    void tearDown() {
        rabbitAdmin.purgeQueue("items", true);
        rabbitListenerEndpointRegistry.stop();
    }

    @Test
    void publish_success() {
        jackPublisher.publishMessages("item");
//        rabbitListenerEndpointRegistry.getListenerContainer(
//                "jackListener"
//        ).start();
    }
}
