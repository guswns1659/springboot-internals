package com.springboot.springbootinternals.assertj.core.presentation;

public interface Representation {

    /**
     * Returns the String representation of the given object. It may or may not be the object's own implementation
     * of toString.
     * @param object
     * @return
     */
    String toStringOf(Object object);

    /**
     * Returns the String representation of the given object with its type and hexadecimal identity hash code so
     * that it can be differentiated from other objects with the same toStringOf(Object) representation.
     * @param object
     * @return
     */
    String unambiguousToStringOf(Object object);
}
