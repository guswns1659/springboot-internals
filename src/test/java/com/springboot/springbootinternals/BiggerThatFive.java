package com.springboot.springbootinternals;

import java.util.function.Predicate;

public class BiggerThatFive<E> implements Predicate<Integer> {

    @Override
    public boolean test(Integer integer) {
        Integer five = 5;
        return integer > five;
    }
}
