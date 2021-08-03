package com.springboot.springbootinternals.assertion;

import java.util.function.Predicate;

/**
 * Assertions for Predicate
 * @param <T>
 */
public class PredicateAssert<T> extends AbstractPredicateAssert<PredicateAssert<T>, T> {

    protected PredicateAssert(Predicate<T> actual) {
        super(actual, PredicateAssert.class);
    }

}
