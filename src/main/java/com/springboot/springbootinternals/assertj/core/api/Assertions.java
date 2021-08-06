package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.util.CheckReturnValue;
import java.util.function.Predicate;

@CheckReturnValue
public class Assertions implements InstanceOfAssertFactories {

    public static <T> PredicateAssert<T> assertJack(Predicate<T> actual) {
        return new PredicateAssert<>(actual);
    }

    // TODO :
//    public static AbstractIntegerAssert<?> assertJack(int actual) {
//        return AssertionsForClassTypes.assertJack(actual);
//    }


}
