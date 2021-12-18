package com.titanic.webmvc.assertj.core.error;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.presentation.Representation;

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
