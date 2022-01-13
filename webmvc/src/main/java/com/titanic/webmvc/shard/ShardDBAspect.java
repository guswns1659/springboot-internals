package com.titanic.webmvc.shard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ShardDBAspect {

    @Around("@annotation(shardDBAccess) && args(userId, ..)")
    public Object around(ProceedingJoinPoint joinPoint, Long userId, ShardDBAccess shardDBAccess) throws Throwable {

        int shardNum = (int) (userId % 3);

        UserHolder.setContext(shardNum);

        Object proceed = joinPoint.proceed();

        // UserHolder clear();
        return proceed;
    }
}
