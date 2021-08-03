package com.springboot.springbootinternals.assertion;

import static com.springboot.springbootinternals.assertion.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static java.util.Objects.requireNonNull;

/**
 * Writable information about an assertion
 */
public class WritableAssertionInfo implements AssertionInfo {

    private Represetation representation;

    public WritableAssertionInfo(Represetation customRepresentation) {
        useRepresentation(
            customRepresentation == null ? CONFIGURATION_PROVIDER.representation() : customRepresentation);
    }

    public void useRepresentation(Represetation newRepresentation) {
        requireNonNull(newRepresentation, "The representation to use should not be null.");
        representation = newRepresentation;
    }

}
