package com.titanic.webmvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MathLearningTest {
    @Test
    void round() {
        double value = 123.4;
        assertThat(Math.round(value)).isEqualTo(123);

        double value2 = 123.5;
        assertThat(Math.round(value2)).isEqualTo(124);
    }

}
