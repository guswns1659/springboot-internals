package com.titanic.webmvc.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UuidTest {

    @Test
    void uuid() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        assertThat(uuid.length()).isLessThan(255);
    }

}
