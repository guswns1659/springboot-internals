package com.titanic.webmvc.assertj.core.internal;


import com.titanic.webmvc.assertj.core.api.AssertionInfo;
import com.titanic.webmvc.assertj.core.util.VisibleForTesting;

import static com.titanic.webmvc.assertj.core.error.ShouldBeEqual.shouldBeEqual;

/**
 * Reusable assertions for Comparables
 */
public class Comparables {

    private final ComparisonStrategy comparisonStrategy;

    @VisibleForTesting
    Failures failures = Failures.instance();

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

    /**
     * Asserts that two T instances are equal
     * @param info - contains information about the assertion
     * @param actual - the actual value.
     * @param expected - the expected value.
     * @param <T> - the type of actual and expected
     */
    public <T> void assertEqual(AssertionInfo info, T actual, T expected) {
        assertNotNull(info, actual);
        if (areEqual(actual, expected)) return;
        throw failures.failure(info, shouldBeEqual(actual, expected, comparisonStrategy, info.representation()));
    }

    protected <T> boolean areEqual(T actual, T expected) {
        return comparisonStrategy.areEqual(actual, expected);
    }

    protected static <T> void assertNotNull(AssertionInfo info, T actual) {
        Objects.instance().assertNotNull(info, actual);
    }
}
