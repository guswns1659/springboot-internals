package com.younghan.two.proxy.config.v6_aop.aspect;

import com.younghan.two.proxy.trace.TraceStatus;
import com.younghan.two.proxy.trace.logtrace.LogTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogTraceAspect {

    private final LogTrace trace;

    public LogTraceAspect(LogTrace trace) {
        this.trace = trace;
    }

    @Around("execution(* com.younghan.two.proxy.app..*(..))")
    public Object logTraceAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {

            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);

            Object result = joinPoint.proceed();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
