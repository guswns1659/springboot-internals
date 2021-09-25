package com.springboot.springbootinternals;

import static com.springboot.springbootinternals.assertj.core.api.Assertions.assertJack;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    public void integer_isEqualTo() {
        // given

        // when
//        assertJack(3).isEqualTo(3);
//        assertJack(3).isEqualTo(4);
//        assertThat(3).isEqualTo(4);
        System.out.println(LocalDateTime.now().toString());
        System.out.println(Calendar.getInstance().toString());
        System.out.println(3);

        // then
    }
}
