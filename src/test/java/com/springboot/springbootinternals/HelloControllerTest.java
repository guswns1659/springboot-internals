package com.springboot.springbootinternals;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

public class HelloControllerTest {


    @Test
    void assertJ() {
        // int
        assertThat(3).isEqualTo(3);
    }
}
