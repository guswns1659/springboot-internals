package com.titanic.messaging.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class JackLockAdvice {

    private String getUserIdFromJoinPoint(JoinPoint joinPoint, LockType lockType) {
        log.info("joinPoint.getArgs() = {}", joinPoint.getArgs());
        return "1234";
    }

    @Around("@annotation(jackLock)")
    public Object JackLockAround(ProceedingJoinPoint joinPoint, JackLock jackLock) throws Throwable {
        String userId = getUserIdFromJoinPoint(joinPoint, jackLock.value());

        log.info("userId = {}", userId);
        log.info("Before method call");
        joinPoint.proceed();
        log.info("After method call");

        return null;
    }
}
