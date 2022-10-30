package com.titanic.messaging.redis.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class JJackRedisTest {

    @Autowired
    private RedisTemplate<String, String> jjackRedisTemplate;

    @Test
    void set_get() {
        String key = "jack-key";
        jjackRedisTemplate.opsForValue().set(key, "comeback");
        Assertions.assertThat(jjackRedisTemplate.opsForValue().get(key)).isEqualTo("comeback");
    }
}
