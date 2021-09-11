package com.springboot.springbootinternals.assertj.core.error;

import java.lang.reflect.Constructor;

/**
 * Access to constructors using Java reflection
 */
public class ConstructorInvoker {

    public Object newInstance(String className, Class<?>[] parameterTypes, Object... parameterValues) throws Exception {
        Class<?> targetType = Class.forName(className);
        Constructor<?> constructor = targetType.getConstructor(parameterTypes);
        return constructor.newInstance((Object) parameterTypes);
    }
}
