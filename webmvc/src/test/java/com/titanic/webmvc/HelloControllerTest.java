package com.titanic.webmvc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloControllerTest {


    @Test
    void assertJ() {
        // int
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        assertThat(integers).isNotIn(3);

        assertThat(3).isNotNull();

    }
}
