package com.springboot.springbootinternals.assertj.core.internal;

/**
 * Implements ComparisonStrategy contract with a comparison sttategy baed on java.util.
 */
public class StandardComparisonStrategy extends AbstractComparisonStrategy{

    private static final StandardComparisonStrategy INSTANCE = new StandardComparisonStrategy();

    public static StandardComparisonStrategy instance() {
        return INSTANCE;
    }

    @Override
    public String asText() {
        return "";
    }

    @Override
    public boolean areEqual(Object actual, Object expected) {
        return java.util.Objects.deepEquals(actual, expected);
    }
}
