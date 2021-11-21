package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * daemon thread : JVM이 daemon thread만 남으면 종료시킨다.
 * user thread : JVM이 user thread만 남으면 종료하지 않는다.
 */
@Slf4j
public class FluxScEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(500))
                .subscribe(s -> log.info("onNext:{}", s));

        log.info("exit");
        TimeUnit.SECONDS.sleep(5);
    }
}
