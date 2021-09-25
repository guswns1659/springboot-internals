package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import com.springboot.springbootinternals.assertj.core.util.CheckReturnValue;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;
import java.util.function.Supplier;

public abstract class AbstractAssert<SELF extends AbstractAssert<SELF, ACTUAL>, ACTUAL> implements
    Assert<SELF, ACTUAL> {

    @VisibleForTesting
    public WritableAssertionInfo info;

    // visibility is protected to allow us write custom assertions that need access to actual
    protected final ACTUAL actual;
    protected final SELF myself;

    // = ConfigurationProvider.CONFIGURATION_PROVIDER.representation(); ?
    private static Representation customRepresentation = null;

    protected AbstractAssert(ACTUAL actual, Class<?> selfType) {
        myself = (SELF) selfType.cast(this);
        this.actual = actual;
        info = new WritableAssertionInfo(customRepresentation);
    }

    /**
     * Alternative method for overridingErrorMessage
     * @param supplier
     * @return
     */
    @CheckReturnValue
    public SELF withFailMessage(Supplier<String> supplier) {
        return overridingErrorMessage(supplier);
    }

    /**
     * Overrides AssertJ default error message by the given one.
     * @param supplier
     * @return
     */
    @CheckReturnValue
    public SELF overridingErrorMessage(Supplier<String> supplier) {
        info.overridingErrorMessage(supplier);
        return myself;
    }
}
