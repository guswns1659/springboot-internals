package com.titanic.messaging.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NewJackPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.new.queue}")
    private String queue;

    public NewJackPublisher(@Qualifier("newRabbitTemplate") RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(Data data) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(data);
        rabbitTemplate.convertAndSend(queue, message);
    }
}

@Component
class NewJackListener {

    @RabbitListener(
            id = "${spring.rabbitmq.new.listener}",
            queues = "${spring.rabbitmq.new.queue}",
            containerFactory = "newListenerContainerFactory")
    public void listen(@Payload Message message) {
        System.out.println("***** listen new message : " + message);
    }
}
