package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.description.Descriptable;

/**
 * Base contract of all assertion objects: the minimum functionality that any assertion object should provide
 */
public interface Assert <SELF extends Assert<SELF, ACTUAL>, ACTUAL> extends Descriptable<SELF>,
    ExtensionPoints<SELF, ACTUAL> {

    /**
     * Verifies that the actual value is equal to the given on.
     * @param expected
     * @return
     */
    SELF isEqualTo(Object expected);

}
