package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.internal.ComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import java.util.Objects;

public class ShouldBeEqual implements AssertionErrorFactory {

    protected final Object actual;
    protected final Object expected;
    protected final MessageFormatter messageFormatter = MessageFormatter.instance();
    protected final ComparisonStrategy comparisonStrategy;
    private Representation representation;

    protected ShouldBeEqual(Object actual, Object expected, ComparisonStrategy comparisonStrategy,
        Representation representation) {
        this.actual = actual;
        this.expected = expected;
        this.comparisonStrategy = comparisonStrategy;
        this.representation = representation;
    }

    public static AssertionErrorFactory shouldBeEqual(Object actual, Object expected,
        ComparisonStrategy comparisonStrategy,
        Representation representation) {
        return new ShouldBeEqual(actual, expected, comparisonStrategy, representation);
    }

    /**
     * Creates an AssertionError indicating that an assertion that verifies that two objects are equal failed. The
     * AssertionError message is built so that it differentiates actual and expected description in case their
     * string representation are the same (like 42 float and 42 double)
     *
     * @param description
     * @param representation
     * @return
     */
    @Override
    public AssertionError newAssertionError(Description description, Representation representation) {
        String message = smartErrorMessage(description, representation);

        return null;
    }

    /**
     * Builds and returns an error message from the given description using expected and actual basic
     * representation if their description differ otherwise use defaultDetailedErrorMessage to represent them
     * differently.
     */
    protected String smartErrorMessage(Description description, Representation representation) {
//        if (actualAndExpectedHaveSameStringRepresentation()) {
//            return
//        }
        return null;

    }

    protected boolean actualAndExpectedHaveSameStringRepresentation() {
        return Objects.equals(representation.toStringOf(actual), representation.toStringOf(expected));
    }
}
