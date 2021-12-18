package com.titanic.webmvc.assertj.core.error;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.presentation.Representation;

/**
 * Factory of error message
 */
public interface ErrorMessageFactory {

    /**
     * Creates a new error message as a result of a failed assertion
     */
    String create(Description d, Representation p);

    String create(Description d);

    String create();
}
