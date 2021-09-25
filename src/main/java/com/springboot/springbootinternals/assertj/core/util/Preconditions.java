package com.springboot.springbootinternals.assertj.core.util;

import static java.lang.String.format;

public class Preconditions {

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not involving any
     * parameters to the calling method.
     *
     * @param expression
     * @param errorMessageTemplate
     * @param errorMessageArgs
     */
    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
        }
    }
}
