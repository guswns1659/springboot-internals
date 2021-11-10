package com.springboot.springbootinternals.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NewJackPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public NewJackPublisher(@Qualifier("newRabbitTemplate") RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(Data data) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend("items", message);
    }
}

@Component
class NewJackListener {

    @RabbitListener(
            id = "jackListener2",
            queues = "items",
            containerFactory = "newListenerContainerFactory")
    public void listen(@Payload Message message) {
        System.out.println("***** listen new message : " + message);
    }
}
