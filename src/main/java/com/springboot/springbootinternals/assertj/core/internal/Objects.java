package com.springboot.springbootinternals.assertj.core.internal;

import static com.springboot.springbootinternals.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import com.springboot.springbootinternals.assertj.core.api.AssertionInfo;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

/**
 * Reusalbe assertions for Objects
 */
public class Objects {

    public static final Objects INSTANCE = new Objects();

    @VisibleForTesting
    Failures failures = Failures.instance();

    public static Objects instance() {
        return INSTANCE;
    }

    public void assertNotNull(AssertionInfo info, Object actual) {
        if (actual == null) throw failures.failure(info, shouldNotBeNull());
    }
}
