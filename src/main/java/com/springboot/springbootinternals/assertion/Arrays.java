package com.springboot.springbootinternals.assertion;

public class Arrays {

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || isEmpty(array);
    }

    private static <T> boolean isEmpty(T[] array) {
        return array.length == 0;
    }

}
