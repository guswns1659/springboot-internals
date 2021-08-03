package com.springboot.springbootinternals.assertion;

/**
 * Provides a Assert for the current object
 */
public interface AssertProvider<A> {

    /**
     * Returns the associated Assert for this object
     * @return
     */
    A assertJack();

}
