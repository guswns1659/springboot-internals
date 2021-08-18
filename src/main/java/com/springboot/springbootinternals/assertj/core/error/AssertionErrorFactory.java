package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

/**
 * Factory of AssertionErrors
 */
public interface AssertionErrorFactory {

    AssertionError newAssertionError(Description description, Representation representation);

}
