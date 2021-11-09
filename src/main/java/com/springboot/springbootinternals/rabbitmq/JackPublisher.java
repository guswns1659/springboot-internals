package com.springboot.springbootinternals.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JackPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishMessages(String item) {
        rabbitTemplate.convertAndSend(
                "items",
                item
        );
    }
}

@Component
@RequiredArgsConstructor
class JackListener {

    @RabbitListener(
            id = "jackListener",
            queues = "items")
    public String receiveMessage(@Payload Message message) {
        System.out.println(message);
        return message.toString();
    }
}
