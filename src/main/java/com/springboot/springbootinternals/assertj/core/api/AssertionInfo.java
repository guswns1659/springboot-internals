package com.springboot.springbootinternals.assertj.core.api;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

/**
 * Information about an assertion
 */
public interface AssertionInfo {


    String overridingErrorMessage();

    Description description();

    Representation representation();
}
