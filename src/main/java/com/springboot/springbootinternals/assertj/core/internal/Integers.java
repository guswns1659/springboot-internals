package com.springboot.springbootinternals.assertj.core.internal;

/**
 * Reusable assertions for Integers
 */
public class Integers extends Numbers<Integer> implements WholeNumbers<Integer>{

    public static final Integers INSTANCE = new Integers();

    /**
     * Returns the singleton instance of this class based on StandardComparisonStrategy.
     */
    public static Integers instance() {
        return INSTANCE;
    }

}
