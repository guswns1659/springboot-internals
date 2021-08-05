package com.springboot.springbootinternals.assertj.core.error;

import static java.lang.String.format;

/**
 * Creates an error message that indicates an assertion that verifies that an object is not null failed
 */
public class ShouldNotBeNull extends BasicErrorMessageFactory {

    private static final ShouldNotBeNull INSTANCE = new ShouldNotBeNull("%nExpecting actual not to be null");

    public static ErrorMessageFactory shouldNotBeNull() {
        return INSTANCE;
    }

    public static ShouldNotBeNull shouldNotBeNull(String label) {
        return new ShouldNotBeNull(format("%nExpecting %s not to be null", label));
    }

    private ShouldNotBeNull(String label) {
    }
}
