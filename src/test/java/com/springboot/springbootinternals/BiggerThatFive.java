package com.springboot.springbootinternals;

import java.util.function.Predicate;

public class BiggerThatFive<E> implements Predicate<Integer> {

    @Override
    public boolean test(Integer integer) {
        Integer five = 5;
        return integer > five;
    }

    // TODO(jack.comeback) :
    @Override
    public boolean test2(Integer integer) {
        Integer five = 5;
        return integer > five;
    }

    @Override
    public boolean test3(Integer integer) {
        Integer five = 5;
        return integer > five;
    }
}
