package com.springboot.springbootinternals.assertion;

public abstract class AbstractAssert<SELF extends AbstractAssert<SELF, ACTUAL>, ACTUAL> implements Assert<SELF, ACTUAL> {

    @VisibleForTesting
    public WritableAssertionInfo info;

    // visibility is protected to allow us write custom assertions that need access to actual
    protected final ACTUAL actual;
    protected final SELF myself;

    // = ConfigurationProvider.CONFIGURATION_PROVIDER.representation(); ?
    private static Represetation customRepresentation = null;



    protected AbstractAssert(ACTUAL actual, Class<?> selfType) {
        myself = (SELF) selfType.cast(this);
        this.actual = actual;
        info = new WritableAssertionInfo(customRepresentation);
    }
}
