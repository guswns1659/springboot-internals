package com.springboot.springbootinternals;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

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
