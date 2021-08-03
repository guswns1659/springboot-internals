package com.springboot.springbootinternals.assertion;

import java.util.function.Predicate;

@CheckReturnValue
public class Assertions implements InstanceOfAssertFactories {

    public static <T> PredicateAssert<T> assertJack(Predicate<T> actual) {
        return new PredicateAssert<>(actual);
    }


}
