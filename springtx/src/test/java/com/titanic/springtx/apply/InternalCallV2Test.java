package com.titanic.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {

    @Autowired
    private ExternalCallService externalCallService;

    @Test
    void external() {
        externalCallService.external();
    }

    @TestConfiguration
    static class config {
        @Bean
        public ExternalCallService callService() {
            return new ExternalCallService(internalCallService());
        }

        @Bean
        public InternalCallService internalCallService() {
            return new InternalCallService();
        }
    }

    @Slf4j
    static class ExternalCallService {

        private final InternalCallService internalCallService;

        public ExternalCallService(InternalCallService internalCallService) {
            this.internalCallService = internalCallService;
        }

        public void external() {
            log.info("call external");
            printTxInfo();
            internalCallService.internal();
        }

        private static void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive = {}", txActive);
        }
    }

    @Slf4j
    static class InternalCallService {
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
