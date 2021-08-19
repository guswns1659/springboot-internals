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

    /**
     * Verifies that the actual value is equal to the given one.
     * @param expected - the given value to compare the actual value to
     * @return - this assertion object
     */
    public SELF isEqualTo(int expected) {
        integers.assertEqual(info, actual, expected);
        return myself;
    }
}
