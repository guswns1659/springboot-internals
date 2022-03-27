package com.titanic.webmvc.tobyspring.chap3.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) throws IOException {
        return template(filePath, (line, result) ->
            result + Integer.valueOf(line)
        );
    }

    public Integer calcMulti(String filePath) throws IOException {
        return template(filePath, (line, result) ->
            result * Integer.valueOf(line)
        );
    }

    private Integer template(String filePath, CalculateStrategy calculateStrategy) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Integer result = 0;
        String line = null;
        while ((line = br.readLine()) != null) {
            result = calculateStrategy.calculate(line, result);
        }
        br.close();
        return result;
    }
}
