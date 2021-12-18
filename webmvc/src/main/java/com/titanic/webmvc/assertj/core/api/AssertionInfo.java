package com.titanic.webmvc.assertj.core.api;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.presentation.Representation;

/**
 * Information about an assertion
 */
public interface AssertionInfo {


    String overridingErrorMessage();

    Description description();

    Representation representation();
}
