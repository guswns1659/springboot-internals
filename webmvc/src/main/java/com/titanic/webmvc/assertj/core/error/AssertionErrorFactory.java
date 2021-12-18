package com.titanic.webmvc.assertj.core.error;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.presentation.Representation;

/**
 * Factory of AssertionErrors
 */
public interface AssertionErrorFactory {

    AssertionError newAssertionError(Description description, Representation representation);

}
