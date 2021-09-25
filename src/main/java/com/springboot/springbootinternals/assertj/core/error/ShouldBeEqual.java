package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.internal.ComparatorBasedComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.internal.ComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import java.util.Objects;

public class ShouldBeEqual implements AssertionErrorFactory {

    private static final String EXPECTED_BUT_WAS_MESSAGE = "%nexpected: %s%nbut was : %s";
    public static final String EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR = EXPECTED_BUT_WAS_MESSAGE + "%n%s";
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
        if (actualAndExpectedHaveSameStringRepresentation()) {
            // This happens for example when actual = 42f and expected = 42d, which will give this error:
            // actual : "42" and expected : "42".
            // JUnit 4 manages this case even worst, it will output something like :
            // "java.lang.String expected:java.lang.String<42.0> but was: java.lang.String<42.0>"
            // which makes things even more confusing since we lost the fact that 42 was a float or a double.
            // It is therefore better to built our own description without using ComparisonFailure, the
            // only drawback is that it won't look nice in IDEs.
            return defaultDetailedErrorMessage(description, representation);
        }
        return null;

    }

    protected boolean actualAndExpectedHaveSameStringRepresentation() {
        return Objects.equals(representation.toStringOf(actual), representation.toStringOf(expected));
    }

    /**
     * Builds and returns an error message from description using detailedExpected() and detailed representation.
     *
     * @param description
     * @param representation
     * @return
     */
    protected String defaultDetailedErrorMessage(Description description, Representation representation) {
        if (comparisonStrategy instanceof ComparatorBasedComparisonStrategy) {
            return messageFormatter.format(description, representation, EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR,
                detailedExpected(), detailedActual(), comparisonStrategy);
        }
        return messageFormatter.format(description, representation, EXPECTED_BUT_WAS_MESSAGE, detailedExpected(), detailedActual());
    }

    protected String detailedExpected() {
        return representation.unambiguousToStringOf(expected);
    }

    protected String detailedActual() {
        return representation.unambiguousToStringOf(actual);
    }
}
