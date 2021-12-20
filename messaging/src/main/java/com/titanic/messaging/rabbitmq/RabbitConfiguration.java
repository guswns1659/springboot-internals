package com.titanic.messaging.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    @Bean("oldListenerContainerFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("oldRabbitConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean("oldRabbitConnectionFactory")
    @Primary
    public ConnectionFactory oldRabbitConnectionFactory(
            @Value("${spring.rabbitmq.old.port}") int port,
            @Value("${spring.rabbitmq.host}") String host,
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
    public RabbitTemplate oldRabbitTemplate(
            @Qualifier("oldRabbitConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("oldRabbitAdmin")
    public RabbitAdmin oldRabbitAdmin(
            @Qualifier("oldRabbitConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean("newRabbitTemplate")
    public RabbitTemplate newRabbitTemplate(
            @Qualifier("newRabbitConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean("newRabbitAdmin")
    public RabbitAdmin newRabbitAdmin(
            @Qualifier("newRabbitConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean("newListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory newListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("newRabbitConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean("newRabbitConnectionFactory")
    public ConnectionFactory newRabbitConnectionFactory(
            @Value("${spring.rabbitmq.new.port}") int port,
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost
    ) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);

        return factory;
    }

}
