package com.titanic.webmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.titanic.webmvc.assertj.core.api.Assertions.assertJack;


public class AssertionTest {


    @Test
    @DisplayName("assertJack_isEqualTo 테스트")
    public void assertjack_isEqualTo() {
        // given
        BiggerThatFive<Integer> biggerThatFive = new BiggerThatFive<>();

        // when
        assertJack(biggerThatFive).isEqualTo(5);

        // then
    }

    @Test
    @DisplayName("assertJack_Integer_isEqualTo 테스트")
    public void integer_isEqualTo_success() {
        // given

        // when
        assertJack(3).isEqualTo(3);

        // then
    }

    @Test
    @DisplayName("asserJack_Integer_fail")
    public void asserjackIntegerFail() {
        // given

        // when
        assertJack(3).isEqualTo(4);

        // then
    }


}
