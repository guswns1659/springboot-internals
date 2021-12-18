package com.titanic.webmvc.assertj.core.internal;

public class ComparatorBasedComparisonStrategy extends AbstractComparisonStrategy {

    @Override
    public String asText() {
        return null;
    }

    @Override
    public boolean areEqual(Object actual, Object expected) {
        return false;
    }

    @Override
    public boolean isStandard() {
        return false;
    }
}
