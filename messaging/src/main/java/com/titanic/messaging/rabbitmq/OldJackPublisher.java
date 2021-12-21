package com.titanic.messaging.rabbitmq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OldJackPublisher {

    private final RabbitTemplate oldRabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.old.queue}")
    private String queue;

    public OldJackPublisher(
            @Qualifier("oldRabbitTemplate") RabbitTemplate oldRabbitTemplate,
            ObjectMapper objectMapper) {
        this.oldRabbitTemplate = oldRabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessages(Data item) throws JsonProcessingException {
        // serialize가 정상적으로 안된다.
        String message = objectMapper.writeValueAsString(item);
//        String message = "items";

        oldRabbitTemplate.convertAndSend(
                queue,
                message
        );
    }
}

@Component
class OldJackListener {

    private final RabbitTemplate newRabbitTemplate;

    @Value("${spring.rabbitmq.old.queue}")
    private String oldNodeQueue;

    @Value("${spring.rabbitmq.new.queue}")
    private String newNodeQueue;

    public OldJackListener(
            @Qualifier("newRabbitTemplate") RabbitTemplate newRabbitTemplate) {
        this.newRabbitTemplate = newRabbitTemplate;
    }

    @RabbitListener(
            id = "${spring.rabbitmq.old.listener}",
            queues = "${spring.rabbitmq.old.queue}")
    public void receiveMessage(@Payload Message message) {
        System.out.println(">>>>> old message " + message);
        newRabbitTemplate.convertAndSend(
                newNodeQueue,
                message
        );
    }
}

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class Data {
    String name;
}
