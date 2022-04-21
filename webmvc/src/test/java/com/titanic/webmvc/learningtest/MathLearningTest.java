package com.titanic.webmvc.learningtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MathLearningTest {
    @Test
    void round() {
        double value = 123.4;
        assertThat(Math.round(value)).isEqualTo(123);

        double value2 = 123.5;
        assertThat(Math.round(value2)).isEqualTo(124);
    }

    @Test
    void toIntExact() {
        assertThatThrownBy(() -> Math.toIntExact(Long.MAX_VALUE))
            .isInstanceOf(ArithmeticException.class);

        assertThat(Math.toIntExact(1234L)).isEqualTo(1234);
    }
}
