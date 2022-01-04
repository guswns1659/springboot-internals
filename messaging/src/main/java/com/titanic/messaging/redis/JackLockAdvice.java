package com.titanic.messaging.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class JackLockAdvice {

    private final RedisTemplate<String, String> jackStringRedisTemplate;

    public JackLockAdvice(RedisTemplate<String, String> jackStringRedisTemplate) {
        this.jackStringRedisTemplate = jackStringRedisTemplate;
    }

    private String getAccountIdFromUserId(JoinPoint joinPoint, LockType lockType) {
        Object[] args = joinPoint.getArgs();
        log.info("joinPoint.getArgs() = {}", args);
        return String.valueOf(args[0]);
    }

    @Around("@annotation(jackLock)")
    public Object JackLockAround(ProceedingJoinPoint joinPoint, JackLock jackLock) throws Throwable {
        String accountId = getAccountIdFromUserId(joinPoint, jackLock.value());

        log.info("accountId = {}", accountId);

        log.info("LOCK start - {}", accountId);

        String key = accountId;
        String value = String.valueOf(System.currentTimeMillis());
        Boolean isSuccessLock;
        try {
            isSuccessLock = jackStringRedisTemplate.opsForValue().setIfAbsent(key, value);

            if (!isSuccessLock) {
                if (isExpiredLock(key)) {
                    jackStringRedisTemplate.delete(key);
                }
                log.info("lock fail, accountId - {}", key);
                throw new RuntimeException();
            }
            jackStringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } catch (RedisConnectionFailureException e) {
            throw e;
        }

        log.info("LOCK success - {}", accountId);

        return joinPoint.proceed();
    }

    private Boolean isExpiredLock(String key) {
        return Optional.ofNullable(jackStringRedisTemplate.opsForValue().get(key))
                .map(lastLockAt -> {
                    long current = System.currentTimeMillis();
                    return (current - Long.parseLong(lastLockAt)) / 1000 > 60;
                }).orElse(false);
    }
}
