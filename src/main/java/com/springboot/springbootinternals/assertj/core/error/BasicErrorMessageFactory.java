package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

public class BasicErrorMessageFactory implements ErrorMessageFactory {

//    @VisibleForTesting
//    MessageFormatter formatter = Mes

    @Override
    public String create(Description d, Representation representation) {
        return "";
    }

    @Override
    public String create(Description d) {
        return null;
    }

    @Override
    public String create() {
        return null;
    }
}
