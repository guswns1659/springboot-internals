package com.titanic.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootTest
public class InitTxTest {
    @Autowired
    private Hello hello;

    @Test
    void context() {
//        hello.postConstruct();
    }

    @TestConfiguration
    static class config {
        @Bean
        public Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        @Transactional
        @PostConstruct
        public void postConstruct() {
            log.info("call postConstruct");
            printTxInfo();
        }

        @Transactional
        @EventListener(ApplicationReadyEvent.class)
        public void eventListener() {
            log.info("call eventListener");
            printTxInfo();
        }

        private static void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive = {}", txActive);
        }
    }
}
