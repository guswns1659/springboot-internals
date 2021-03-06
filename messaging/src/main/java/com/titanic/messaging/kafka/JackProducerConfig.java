package com.titanic.messaging.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JackProducerConfig {

    @Value("${spring.kafka.jack.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.jack.authentication.username}")
    private String username;

    @Value("${spring.kafka.jack.authentication.password}")
    private String password;

    /**
     * Producer Tutorial 1. Add kafka dependencies to build.gradle 2. Set ProducerConfig 3. Set Producer 4. Test
     */

    // ProducerFactory
    @Bean
    public ProducerFactory<String, String> jackProducerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        // LOGIC : embeddedKafka가 SASL 프로토콜을 지원하지 않아 사용하지 않음.
//        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
//        properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
//        properties.put(SaslConfigs.SASL_JAAS_CONFIG, String.format("%s required username=\"%s\" password=\"%s\";",
//            ScramLoginModule.class.getName(), username, password));

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
