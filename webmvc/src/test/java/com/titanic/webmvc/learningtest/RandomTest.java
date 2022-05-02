package com.titanic.webmvc.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

public class RandomTest {

    @Test
    void random_10digits() {
        long random = getRandom();
        System.out.println(random);
        assertThat(random).isGreaterThan(1000000000);
    }

    private long getRandom() {
        return 1000000000 + new Random(9000000000L).nextInt();
    }

}
