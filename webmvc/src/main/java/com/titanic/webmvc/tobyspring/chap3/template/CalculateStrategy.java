package com.titanic.webmvc.tobyspring.chap3.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface CalculateStrategy {

    Integer calculate(String line, Integer result) throws IOException;
}
