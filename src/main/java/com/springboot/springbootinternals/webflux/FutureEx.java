package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws InterruptedException {
        // maxium 제한이 없고 처음엔 풀에 스레드가 없다. 사용한 후에 반납하면 그대로 가지고 있다.
        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            log.info("Async");
        });

        log.info("Exit");
    }
}
