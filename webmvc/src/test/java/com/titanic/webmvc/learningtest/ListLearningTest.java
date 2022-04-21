package com.titanic.webmvc.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ListLearningTest {

    @Test
    void nullGiven_ShouldIncreaseListSize() {
        List<String> strings = new ArrayList<>();
        strings.add(null);
        assertThat(strings.size()).isEqualTo(1);
    }
}
