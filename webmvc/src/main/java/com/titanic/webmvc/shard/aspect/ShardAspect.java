package com.titanic.webmvc.shard.aspect;

import com.titanic.webmvc.shard.Shard;
import com.titanic.webmvc.shard.ShardDb;
import com.titanic.webmvc.shard.UserContextHolder;
import com.titanic.webmvc.shard.service.ShardNumChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "spring.config.activate.on-profile", havingValue = "shard")
public class ShardAspect {

    private final ShardNumChecker shardNumChecker;

    @Around("@annotation(shard) && args(userId, ..)")
    public Object shardAround(ProceedingJoinPoint joinPoint, Shard shard, Long userId) throws Throwable {
        if (userId == null) {
            throw new IllegalStateException("Required userId for shardDB");
        }

        ShardDb shardDb = shardNumChecker.processSharding(userId);
        UserContextHolder.setShardDb(shardDb);

        Object result = joinPoint.proceed();
        UserContextHolder.clearShardDb();
        return result;
    }
}
