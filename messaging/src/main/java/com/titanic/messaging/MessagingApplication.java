package com.titanic.messaging;

import com.titanic.messaging.redis.test.JJackRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.titanic.messaging.redis.test"})
//@EnableRabbit
//@EnableKafka
@EnableAspectJAutoProxy
public class MessagingApplication {

    @Autowired
    private JJackRedisService jjackRedisService;

    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        log.info("jjackRedisService proxy = {}", AopUtils.isAopProxy(jjackRedisService));
        log.info("jjackRedisService class = {}", jjackRedisService.getClass());
        jjackRedisService.set(12345L);
    }
}
