package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

public class AssertionErrorCreator {

    @VisibleForTesting
    ConstructorInvoker constructorInvoker;

    public AssertionErrorCreator() {
        this(new ConstructorInvoker());
    }

    public AssertionErrorCreator(
        ConstructorInvoker constructorInvoker) {
        this.constructorInvoker = constructorInvoker;
    }

    public AssertionError assertionError(String message) {
        return new AssertionError(message);
    }
}
