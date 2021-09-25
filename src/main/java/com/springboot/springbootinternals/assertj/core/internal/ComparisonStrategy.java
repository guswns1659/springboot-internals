package com.springboot.springbootinternals.assertj.core.internal;

/**
 * Describes the contract to implement a consistent comparision strategy that covers:
 * - comparing two objects for equality and order
 * - knowing if an object belongs to a group of objects (collection/array)
 * - determining duplicates in a group of objects (collection/array)
 * - string specific comparison
 */
public interface ComparisonStrategy {

    boolean areEqual(Object actual, Object expected);

    /**
     * Return true if comparison strategy is default/standard, false otherwise
     * @return
     */
    boolean isStandard();
}
