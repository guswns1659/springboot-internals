package com.springboot.springbootinternals.assertion;

/**
 * A single method factory interface to create an Assert for a given value. This factory method typically wraps a
 * call to assertThat(t) to produce a concrete assert type ASSERT for the input element of type T.
 *
 * @param <T>
 * @param <ASSERT>
 */
@FunctionalInterface
public interface AssertFactory<T, ASSERT extends Assert<?, ?>> {

    /**
     * Creates the custom Assert object for the given element value. Typically this will just invoke
     * assertThat(t)
     *
     * @param t
     * @return
     */
    ASSERT createAssert(T t);

}
