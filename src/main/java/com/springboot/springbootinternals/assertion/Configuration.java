package com.springboot.springbootinternals.assertion;

import static com.springboot.springbootinternals.assertion.StandardRepresentation.STANDARD_REPRESENTATION;

/**
 * All configuration settings for AssertJ Core.
 */
public class Configuration {

    // default values
    public static final Configuration DEFAULT_CONFIGURATION = new Configuration();

    /**
     * Applies this configuration to AssertJ and prints it.
     */
    public void applyAndDisplay() {
        apply();
        System.out.println(describe());

    }

    // TODO : impl
    private String describe() {
        return "";
    }

    // TODO : impl
    private void apply() {

    }

    boolean hasCustomRepresentation() {
        return representation() != STANDARD_REPRESENTATION;
    }

    public Represetation representation() {
        return STANDARD_REPRESENTATION;
    }
}
