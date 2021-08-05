package com.springboot.springbootinternals.assertj.core.api;

import java.util.function.Predicate;

/**
 * Assertions for Predicate
 * @param <T>
 */
public class PredicateAssert<T> extends AbstractPredicateAssert<PredicateAssert<T>, T> {

    public PredicateAssert(Predicate<T> actual) {
        super(actual, PredicateAssert.class);
    }

}
