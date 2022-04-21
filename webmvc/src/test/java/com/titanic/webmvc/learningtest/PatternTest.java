package com.titanic.webmvc.learningtest;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

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
}
