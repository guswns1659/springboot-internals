package com.springboot.springbootinternals.rabbitmq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
    private final ObjectMapper objectMapper;

    public void publishMessages(Data item) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(item);

        rabbitTemplate.convertAndSend(
                "items",
                message
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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class Data {
    String name;
}
