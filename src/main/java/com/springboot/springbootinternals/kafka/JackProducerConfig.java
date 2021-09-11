package com.springboot.springbootinternals.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.scram.ScramLoginModule;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class JackProducerConfig {

    @Value("${spring.kafka.jack.bootstrap-servers")
    private String bootstrapAddress;

    @Value("${spring.kafka.jack.authentication.username}")
    private String username;

    @Value("${spring.kafka.jack.authentication.password}")
    private String password;

    /** Producer Tutorial
     *  1. Add kafka dependencies to build.gradle
     *  2. Set ProducerConfig
     *  3. Set Producer
     *  4. Test
     */

    // ProducerFactory
    @Bean
    public ProducerFactory<String, String> jackProducerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT);
        properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("%s required username=\"%s\" password=\"%s\";",
            ScramLoginModule.class.getName(), username, password));

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(properties);
    }


    // kafkaTemplate
    @Bean
    public KafkaTemplate<String, String> jackKafkaTemplate(
        ProducerFactory<String, String> jackProducerFactory
    ) {
        return new KafkaTemplate<>(jackProducerFactory);
    }

}
