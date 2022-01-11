package com.titanic.webmvc.shard;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class ShardAspect {

    private final ShardNumChecker shardNumChecker;

    @Around("@annotation(shard) && args(userId)")
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
