package com.titanic.webmvc.logback;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    private static Logger logger = LoggerFactory.getLogger("ROLLING");

    @Test
    @DisplayName("sizeAndTimeBased 테스트")
    void logger() {
        for (int i = 0; i <= 100000; i++) {
            logger.info("write log2");

            try {
                Thread.sleep(1L);
            } catch (final InterruptedException e) {
                logger.error("an error occurred", e);
            }
        }
    }
}
