package com.titanic.messaging.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class JackLockAdvice {

    private final RedisTemplate<String, String> jackStringRedisTemplate;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public JackLockAdvice(RedisTemplate<String, String> jackStringRedisTemplate, UserRepository userRepository, EntityManager entityManager) {
        this.jackStringRedisTemplate = jackStringRedisTemplate;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    private String getAccountIdFromUserId(JoinPoint joinPoint, LockType lockType) {
        Object[] args = joinPoint.getArgs();
        log.info("joinPoint.getArgs() = {}", args);
        Optional<User> maybeUser = userRepository.findByAccountId(String.valueOf(args[0]));
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            entityManager.detach(user);
            return user.getAccountId();
        }
        return "";
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
                log.info("Already locked, accountId - {}", key);
                throw new RuntimeException();
            }
            jackStringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis fail, accountId - {}", accountId);
            jackStringRedisTemplate.delete(key);
            throw e;
        }

        log.info("LOCK success - {}", accountId);

        try {
            return joinPoint.proceed();
        } finally {
            releaseLock(key);
        }
    }

    private void releaseLock(String key) {
        log.info("LOCK release - {}", key);
        try {
            jackStringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.info("LOCK release fail");
        }
    }

    private Boolean isExpiredLock(String key) {
        return Optional.ofNullable(jackStringRedisTemplate.opsForValue().get(key))
                .map(lastLockAt -> {
                    long current = System.currentTimeMillis();
                    return (current - Long.parseLong(lastLockAt)) / 1000 > 60;
                }).orElse(false);
    }
}
