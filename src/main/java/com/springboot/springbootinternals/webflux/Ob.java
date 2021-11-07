package com.springboot.springbootinternals.webflux;

import java.util.Arrays;

public class Ob {

    /** 리액티브란?
     *  발생한 이벤트에 대해서 대응하는 방식을 의미한다.
     *  duality
     *  observer pattern
     *  Reactive Stream : 자바를 사용하는 회사들이 만든 리액티브 표준을 의미한다. java9 API에 들어감.
     */
    public static void main(String[] args) {
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i : iterable) {
            System.out.println(i);
        }
    }
}
