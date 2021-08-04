package com.springboot.springbootinternals.assertion;

/**
 * Factory of error message
 */
public interface ErrorMessageFactory {

    /**
     * Creates a new error message as a result of a failed assertion
     */
    String create(Description d, Represetation p);

    String create(Description d);

    String create();
}
