package com.springboot.springbootinternals.assertion;

/**
 * AssertFactory decorator which casts the input value to the given type before invoking the decorated
 * AssertFactory
 */
public class InstanceOfAssertFactory<T, ASSERT extends AbstractAssert<?, ?>> implements AssertFactory<Object, ASSERT> {

    @Override
    public ASSERT createAssert(Object o) {
        return null;
    }
}
