package com.titanic.webmvc.assertj.core.api;

import static com.titanic.webmvc.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static java.util.Objects.requireNonNull;

/**
 * AssertFactory decorator which casts the input value to the given type before invoking the decorated
 * AssertFactory
 */
public class InstanceOfAssertFactory<T, ASSERT extends AbstractAssert<?, ?>> implements
    AssertFactory<Object, ASSERT> {

    private final Class<T> type;
    private final AssertFactory<T, ASSERT> assertFactory;

    public InstanceOfAssertFactory(Class<T> type, AssertFactory<T, ASSERT> assertFactory) {
        this.type = requireNonNull(type, shouldNotBeNull("type").create());
        this.assertFactory = requireNonNull(assertFactory, "assertFactory");
    }

    @Override
    public ASSERT createAssert(Object o) {
        return null;
    }
}
