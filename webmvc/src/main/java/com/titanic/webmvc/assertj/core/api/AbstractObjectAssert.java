package com.titanic.webmvc.assertj.core.api;

/**
 * Base class for all implementations of assertions for Objects
 */
// TODO :
public class AbstractObjectAssert<SELF extends AbstractObjectAssert<SELF, ACTUAL>, ACTUAL> extends AbstractAssert<SELF, ACTUAL> {

    protected AbstractObjectAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    @Override
    public SELF isEqualTo(Object expected) {
        return null;
    }
}
