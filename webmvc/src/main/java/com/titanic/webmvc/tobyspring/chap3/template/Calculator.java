package com.titanic.webmvc.tobyspring.chap3.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) throws IOException {
        return context(filePath, br -> {
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            br.close();
            return sum;
        });
    }

    public Integer calcMulti(String filePath) throws IOException {
        return context(filePath, br -> {
            Integer result = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                result *= Integer.valueOf(line);
            }
            br.close();
            return result;
        });
    }

    private Integer context(String filePath, CalculateStrategy calculateStrategy) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        return calculateStrategy.calculate(bufferedReader);
    }
}
