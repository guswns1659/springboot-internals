package com.titanic.webmvc.learningtest;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PatternTest {

    private Pattern pattern;

    @Test
    void compile_find() {
        pattern = Pattern.compile("[+-/*]?[0-9]+[+-/*]?");
        Matcher matcher = pattern.matcher("abvcd+3");
        assertThat(matcher.find()).isTrue();
        assertThat(matcher.group()).isEqualTo("+3");

        matcher = pattern.matcher("3");
        assertThat(matcher.find()).isTrue();
    }

    @Test
    void compile_matches() {
        pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher("123423");
        assertThat(matcher.matches()).isTrue();

        matcher = pattern.matcher("-123423");
        assertThat(matcher.matches()).isFalse();
    }

    @Test
    void compile_matches2() {
        pattern = Pattern.compile("[0-9]+|[+-/*]");
        Matcher matcher = pattern.matcher("123");
        assertThat(matcher.matches()).isTrue();

        matcher = pattern.matcher("-");
        assertThat(matcher.matches()).isTrue();

        matcher = pattern.matcher("9-");
        assertThat(matcher.matches()).isFalse();

        matcher = pattern.matcher("+1239");
        assertThat(matcher.matches()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1, 1, 2, 3, 4, 5, 46"})
    void compile_matches5(String number) {
        pattern = Pattern.compile("[1-9]|1[0-9]|2[0-9]|3[0-9]|4[0-5]");
        String[] splitNumber = number.split(",");
        for (String each : splitNumber) {
            each = each.trim();
            System.out.print(each + " : ");
            boolean isValid = pattern.matcher(each).matches();
            System.out.println(isValid);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"a, b, 2, 3, 4, 5, 46"})
    void compile_matches6(String number) {
        pattern = Pattern.compile("[1-9]");
        String[] splitNumber = number.split(",");
        for (String each : splitNumber) {
            System.out.print(each + " : ");
            boolean isValid = pattern.matcher(each).find();
            System.out.println(isValid);
        }
    }
}
