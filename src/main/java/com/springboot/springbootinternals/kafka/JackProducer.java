package com.springboot.springbootinternals.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class JackProducer {

    private final KafkaTemplate<String, String> jackKafkaTemplate;
    private final String jackTopic;

    public JackProducer(
        KafkaTemplate<String, String> jackKafkaTemplate,
        @Value("${spring.kafka.jack.topics.jack-topic") String jackTopic) {
        this.jackKafkaTemplate = jackKafkaTemplate;
        this.jackTopic = jackTopic;
    }

    public void sendJackMessage(@Nullable String key, String message) {
        ListenableFuture<SendResult<String, String>> future1;
        if (key == null) {
            future1 = this.jackKafkaTemplate
                .send(jackTopic, message);
        } else {
            future1 = this.jackKafkaTemplate
                .send(jackTopic, key, message);
        }
        future1.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Fail to producing to jack Topic, message = {}, errorMessage = {}", message,
                    ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success Producing to jack topic, offset = {}, when = {}",
                    result.getRecordMetadata().offset(), result.getRecordMetadata().timestamp());
            }
        });
    }
}
