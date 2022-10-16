package com.titanic.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    private CallService callService;

    @Test
    void aopCheck() {
        log.info("callService class = {}", callService.getClass());
        assertThat(AopUtils.isAopProxy(callService)).isTrue();
    }

    @Test
    void external() {
        callService.external();
    }

    @Test
    void internal() {
        callService.internal();
    }

    @TestConfiguration
    static class config {
        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {

        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private static void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive = {}", txActive);
        }
    }
}
