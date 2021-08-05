package com.springboot.springbootinternals.assertj.core.api;

import java.util.function.Predicate;

/**
 * Assertions for Predicate
 */
public class AbstractPredicateAssert<SELF extends AbstractPredicateAssert<SELF, T>, T> extends
    AbstractAssert<SELF, Predicate<T>> {


    protected AbstractPredicateAssert(Predicate<T> actual, Class<?> selfType) {
        super(actual, selfType);
    }
    // TODO : 왜 accepts가 아니라 isEqualTo를 상속하지?
    @Override
    public SELF isEqualTo(Object expected) {
        return null;
    }
}
