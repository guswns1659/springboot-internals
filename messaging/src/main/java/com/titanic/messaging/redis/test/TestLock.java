package com.titanic.messaging.redis.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestLock {
    TestLockType lockType() default TestLockType.PAY_ACCOUNT_ID;
}
