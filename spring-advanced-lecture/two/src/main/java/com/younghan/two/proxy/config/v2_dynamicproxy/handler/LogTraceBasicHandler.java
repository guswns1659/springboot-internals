package com.younghan.two.proxy.config.v2_dynamicproxy.handler;

import com.younghan.two.proxy.trace.TraceStatus;
import com.younghan.two.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace trace;

    public LogTraceBasicHandler(Object target, LogTrace trace) {
        this.target = target;
        this.trace = trace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            String clazz = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();

            String message = clazz + "." + methodName + "()";
            status = trace.begin(message);

            Object result = method.invoke(target, args);

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
