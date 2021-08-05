package com.springboot.springbootinternals.assertj.core.api;

import static com.springboot.springbootinternals.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static java.util.Objects.requireNonNull;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

/**
 * Writable information about an assertion
 */
public class WritableAssertionInfo implements AssertionInfo {

    private Representation representation;

    public WritableAssertionInfo(Representation customRepresentation) {
        useRepresentation(
            customRepresentation == null ? CONFIGURATION_PROVIDER.representation() : customRepresentation);
    }

    public void useRepresentation(Representation newRepresentation) {
        requireNonNull(newRepresentation, "The representation to use should not be null.");
        representation = newRepresentation;
    }

}
