package com.springboot.springbootinternals.assertion;

/**
 * Standard java object representation
 */
public class StandardRepresentation implements Represetation {

    // can share this as StandardRepresentation has no state
    public static final StandardRepresentation STANDARD_REPRESENTATION = new StandardRepresentation();

}
