package com.titanic.messaging.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JackRedisConfig {

    @Bean
    public JedisConnectionFactory connectionFactory(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.password}") String password,
            @Value("${spring.redis.port}") int port
    ) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestOnBorrow(false);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration
                .builder()
                .usePooling()
                .poolConfig(jedisPoolConfig)
                .build();

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public <T> RedisTemplate<String, T> JackStringRedisTemplate(RedisConnectionFactory factory) {
        return getRedisTemplate(factory, new StringRedisSerializer());
    }

    private <T> RedisTemplate<String, T> getRedisTemplate(RedisConnectionFactory factory, StringRedisSerializer serializer) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }
}
