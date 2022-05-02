package com.titanic.webmvc.learningtest;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class IntStreamTest {

    @Test
    void range() {
        assertThat(IntStream.rangeClosed(1, 45).boxed().collect(Collectors.toList()).size()).isEqualTo(45);
    }

    @Test
    void shuffle() {
        List<Integer> numbers = IntStream.rangeClosed(1, 45).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers);
        List<Integer> subList = numbers.subList(0, 6);
        Collections.sort(subList);
        System.out.println(subList);
    }
}
