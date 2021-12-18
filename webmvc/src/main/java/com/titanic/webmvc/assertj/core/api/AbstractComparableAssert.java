package com.titanic.webmvc.assertj.core.api;

import com.titanic.webmvc.assertj.core.internal.Comparables;
import com.titanic.webmvc.assertj.core.util.VisibleForTesting;

/**
 * Base class for all implementations of ComparableAssert
 */
// TODO :
public class AbstractComparableAssert<SELF extends AbstractComparableAssert<SELF, ACTUAL>, ACTUAL extends Comparable<? super ACTUAL>> extends
    AbstractObjectAssert<SELF, ACTUAL> implements ComparableAssert<SELF, ACTUAL> {

    @VisibleForTesting
    Comparables comparables = new Comparables();

    protected AbstractComparableAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }
}
