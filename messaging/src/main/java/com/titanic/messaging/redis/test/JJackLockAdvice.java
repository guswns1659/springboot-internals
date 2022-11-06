package com.titanic.messaging.redis.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JJackLockAdvice {
    private final RedisTemplate<String, String> jjackRedisTemplate;

    /**
     * - lockType에 따라 key 꺼내오는 공통로직
     * - key와 value를 레디스에 셋팅한다.
     *      - key 셋팅 실패 시 exp인데, 만료된 키면 지우고 Exp 날린다.
     *      - key 셋팅 성공 시 60초 만료시간 설정한다.
     * - target.proceed() 진행한다.
     * - 마지막에 key 지운다.
     */
    @Around("@annotation(testLock)")
    public Object joinPoint(ProceedingJoinPoint joinPoint, TestLock testLock) throws Throwable {
        TestLockType testLockType = testLock.lockType();
        log.info("getArgs()" + Arrays.toString(joinPoint.getArgs()));

        Object lockKey;
        if (testLockType == TestLockType.PAY_ACCOUNT_ID) {
            lockKey = joinPoint.getArgs()[0];
        } else {
            lockKey = Arrays.stream(joinPoint.getArgs())
                    .filter(args -> args.equals("userId"))
                    .findAny()
                    .orElse("");
        }

        String key = "key_" + lockKey;
        String value = String.valueOf(System.currentTimeMillis());

        try {
            Boolean isSuccess = jjackRedisTemplate.opsForValue().setIfAbsent(key, value);
            if (!isSuccess) {
                String existedKey = Optional.ofNullable(jjackRedisTemplate.opsForValue().get(key))
                        .orElseThrow(() -> new IllegalStateException("lock 실패"));

                boolean expired = (Long.parseLong(value) - Long.parseLong(existedKey)) / 1000 > 60;
                if (expired) {
                    jjackRedisTemplate.delete(key);
                }
                throw new IllegalStateException("이미 lock이 있습니다.");
            }
            jjackRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } catch (RedisConnectionFailureException e) {
            jjackRedisTemplate.delete(key);
            throw new IllegalStateException("trying lock fail");
        }

        try {
            joinPoint.proceed();
        } finally {
            jjackRedisTemplate.delete(key);
        }

        return null;
    }
}
