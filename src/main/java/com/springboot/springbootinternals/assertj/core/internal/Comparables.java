package com.springboot.springbootinternals.assertj.core.internal;

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


}
