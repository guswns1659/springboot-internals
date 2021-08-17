package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.internal.Integers;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

/**
 * Base class for all implementations of assertions for Integers
 */
public abstract class AbstractIntegerAssert<SELF extends AbstractIntegerAssert<SELF>> extends
    AbstractComparableAssert<SELF, Integer> implements NumberAssert<SELF, Integer> {

    @VisibleForTesting
    Integers integers = Integers.instance();

    protected AbstractIntegerAssert(Integer actual, Class<?> selfType) {
        super(actual, selfType);
    }


}
