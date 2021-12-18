package com.titanic.webmvc.assertj.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Findbugs handles any annotation with name "CheckReturnValue" in return value check
 */
@Target({
    ElementType.CONSTRUCTOR,
    ElementType.METHOD,
    ElementType.PACKAGE,
    ElementType.TYPE
})
@Retention(RetentionPolicy.CLASS)
public @interface CheckReturnValue {

}
