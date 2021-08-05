package com.springboot.springbootinternals.core;

/**
 * Utility methods related to arrays
 */
public class Arrays {

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || isEmpty(array);
    }

    public static <T> boolean isEmpty(T[] array) {
        return array.length == 0;
    }

}
