package com.titanic.webmvc.tobyspring.chap3.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) throws IOException {
        return template(filePath, (line, result) ->
            result + Integer.valueOf(line), 0
        );
    }

    public Integer calcMulti(String filePath) throws IOException {
        return template(filePath, (line, result) ->
            result * Integer.valueOf(line), 1
        );
    }

    public String concatenate(String filePath) throws IOException {
        return template(filePath, (line, result) ->
            result + line, "");
    }

    private <T> T template(String filePath, CalculateStrategy<T> calculateStrategy, T initValue) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        T result = initValue;
        String line = null;
        while ((line = br.readLine()) != null) {
            result = calculateStrategy.calculate(line, result);
        }
        br.close();
        return result;
    }
}
