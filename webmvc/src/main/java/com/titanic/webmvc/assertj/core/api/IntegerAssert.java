package com.titanic.webmvc.assertj.core.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Assertion methods for Integers
 */
public class IntegerAssert extends AbstractIntegerAssert<IntegerAssert> {

    public IntegerAssert(Integer actual) {
        super(actual, IntegerAssert.class);
    }

    public IntegerAssert(AtomicInteger actual) {
        this(actual == null ? null : actual.get());
    }
}
