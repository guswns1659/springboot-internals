package com.titanic.webmvc.tobyspring.chap3.template;

public class SumCalculateStrategy implements CalculateStrategy{

    @Override
    public Integer calculate(String line) {
        return Integer.valueOf(line);
    }
}
