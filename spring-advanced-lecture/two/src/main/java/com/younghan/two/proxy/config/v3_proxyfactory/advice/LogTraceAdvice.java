package com.younghan.two.proxy.config.v3_proxyfactory.advice;

import com.younghan.two.proxy.trace.TraceStatus;
import com.younghan.two.proxy.trace.logtrace.LogTrace;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class LogTraceAdvice implements MethodInterceptor {

    private final LogTrace trace;

    public LogTraceAdvice(LogTrace trace) {
        this.trace = trace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceStatus status = null;
        try {

            Method method = invocation.getMethod();
            String clazz = method.getDeclaringClass().getSimpleName();
            String methodName = method.getName();

            String message = clazz + "." + methodName + "()";
            status = trace.begin(message);

            Object result = invocation.proceed();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
