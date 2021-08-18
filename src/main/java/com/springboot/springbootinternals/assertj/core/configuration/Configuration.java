package com.springboot.springbootinternals.assertj.core.configuration;

import static com.springboot.springbootinternals.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

/**
 * All configuration settings for AssertJ Core.
 */
public class Configuration {

    // default values
    public static final Configuration DEFAULT_CONFIGURATION = new Configuration();

    public static final boolean REMOVE_ASSERTJ_RELATED_ELEMENTS_FROM_STACK_TRACE = true;

    /**
     * Applies this configuration to AssertJ and prints it.
     */
    public void applyAndDisplay() {
        apply();
        System.out.println(describe());

    }

    // 구현하려고 했으니 현 상황에 불필요
    private String describe() {
        return "";
    }

    // 구현하려고 했으니 현 상황에 불필요
    private void apply() {

    }

    boolean hasCustomRepresentation() {
        return representation() != STANDARD_REPRESENTATION;
    }

    public Representation representation() {
        return STANDARD_REPRESENTATION;
    }
}
