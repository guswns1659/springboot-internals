package com.springboot.springbootinternals.assertj.core.internal;

import com.springboot.springbootinternals.assertj.core.api.AssertionInfo;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

/**
 * Reusable assertions for Comparables
 */
public class Comparables {

    private final ComparisonStrategy comparisonStrategy;

    /**
     * Build a Comparables using a StandardComparisonStrategy
     */
    @VisibleForTesting
    public Comparables() {
        this(StandardComparisonStrategy.instance());
    }

    public Comparables(ComparisonStrategy comparisonStrategy) {
        this.comparisonStrategy = comparisonStrategy;
    }

     // TODO :
//    public <T> void assertEqual(AssertionInfo info, T actual, T expected) {
//        assertNotNull(info, actual);
//    }

//    protected static <T> void assertNotNull(AssertionInfo info, T actual) {
//        Objects.instance().assertNotNull(info, actual);
//    }


}
