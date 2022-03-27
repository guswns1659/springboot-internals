package com.titanic.webmvc.tobyspring.chap3;

import static org.assertj.core.api.Assertions.assertThat;

import com.titanic.webmvc.tobyspring.chap3.template.Calculator;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("numbers.txt").getPath());
        assertThat(sum).isEqualTo(10);
    }

}
