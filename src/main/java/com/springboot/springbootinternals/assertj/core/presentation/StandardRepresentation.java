package com.springboot.springbootinternals.assertj.core.presentation;

/**
 * Standard java object representation
 */
public class StandardRepresentation implements Representation {

    // can share this as StandardRepresentation has no state
    public static final StandardRepresentation STANDARD_REPRESENTATION = new StandardRepresentation();

    @Override
    public String toStringOf(Object object) {
        return null;
    }
}
