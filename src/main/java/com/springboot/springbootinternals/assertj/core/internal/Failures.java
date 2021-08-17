package com.springboot.springbootinternals.assertj.core.internal;

import com.springboot.springbootinternals.assertj.core.api.AssertionInfo;
import com.springboot.springbootinternals.assertj.core.error.ErrorMessageFactory;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

/**
 * Failure actions
 */
public class Failures {

    public static final Failures INSTANCE = new Failures();

    @VisibleForTesting
    Failures() {}

    public static Failures instance() {
        return INSTANCE;
    }

    // TODO :
//    public AssertionError failure(AssertionInfo info, ErrorMessageFactory messageFactory) {
//    }
}
