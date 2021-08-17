package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.util.CheckReturnValue;

@CheckReturnValue
public class AssertionsForClassTypes {

    /**
     * Creates a new instance of IntegerAssert
     */
    public static AbstractIntegerAssert<?> assertJack(int actual) {
        return new IntegerAssert(actual);
    }
}
