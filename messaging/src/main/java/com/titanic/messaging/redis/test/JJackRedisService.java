package com.titanic.messaging.redis.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class JJackRedisService {
    private final RedisTemplate<String, String> jjackRedisTemplate;

    @TestLock(lockType = TestLockType.PAY_ACCOUNT_ID)
    public void set(Long payAccountId) {
        Assert.notNull(jjackRedisTemplate.opsForValue().get("key_" + payAccountId), "NPE");
    }
}
