package com.springboot.springbootinternals.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(
            ObjectMapper objectMapper
    ) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean("oldRabbitConnectionFactory")
    @Primary
    public ConnectionFactory oldRabbitConnectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost
    ) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);

        return cachingConnectionFactory;
    }

    @Bean("oldRabbitTemplate")
    @Primary
    public RabbitTemplate oldRabbitTemplate(@Qualifier("oldRabbitConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    // TODO(jack.comeback) : newConnectionFactory, newRabbitTemplate 추가

}
