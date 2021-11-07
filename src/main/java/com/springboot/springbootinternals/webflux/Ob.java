package com.springboot.springbootinternals.webflux;

import java.util.Iterator;

public class Ob {

    /**
     * 리액티브란? 발생한 이벤트에 대해서 대응하는 방식을 의미한다.
     * duality.
     * observer pattern.
     * Reactive Stream : 자바를 사용하는 회사들이 만든 리액티브 표준을 의미한다. java9 API에 들어감.
     */
    public static void main(String[] args) {
        Iterable<Integer> iterable = () ->
                new Iterator<>() {
                    final static int MAX = 10;
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };

        for (Integer i : iterable) {
            System.out.println(i);
        }
    }
}
