package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.util.CheckReturnValue;
import java.util.function.Predicate;

/**
 * Entry point for assertion methods for different types. Each method in this class is a static factory for a
 * type-specific assertion object.
 */
@CheckReturnValue
public class Assertions implements InstanceOfAssertFactories {

    public static <T> PredicateAssert<T> assertJack(Predicate<T> actual) {
        return new PredicateAssert<>(actual);
    }

    /**
     * Creates a new instance of IntegerAssert
     *
     * @param actual - the actual value.
     * @return : the created assertion object.
     */
    public static AbstractIntegerAssert<?> assertJack(int actual) {
        return AssertionsForClassTypes.assertJack(actual);
    }
}
