package com.titanic.webmvc.assertj.core.internal;

import com.titanic.webmvc.assertj.core.api.AssertionInfo;
import com.titanic.webmvc.assertj.core.util.VisibleForTesting;

import static com.titanic.webmvc.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;

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
