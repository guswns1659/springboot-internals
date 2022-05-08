package com.titanic.webmvc.learningtest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Junit5Test {

    public static Stream<Arguments> test() {
        return Stream.of(
                Arguments.of(new User("name"))
        );
    }

    @ParameterizedTest
    @MethodSource("test")
    void methodSource(User user) {
        assertThat(user).isEqualTo(new User("name"));
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
