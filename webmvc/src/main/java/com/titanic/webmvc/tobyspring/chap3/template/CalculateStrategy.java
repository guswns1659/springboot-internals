package com.titanic.webmvc.tobyspring.chap3.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface CalculateStrategy<T> {

    T calculate(String line, T result) throws IOException;
}
