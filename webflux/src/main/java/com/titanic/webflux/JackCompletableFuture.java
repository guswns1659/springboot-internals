package com.titanic.webflux;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JackCompletableFuture {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(10);

        // Add 2, multiple 2.
        CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            return 1;
        }, es)
            .thenApplyAsync(n -> {
                log.info("thenApply = {} ", n);
                return n + 2;
            }, es)
            .thenApplyAsync(n -> {
                log.info("thenApply = {} ", n);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return n * 2;
            }, es)
            .thenAccept(System.out::println);

        ForkJoinPool.commonPool().awaitQuiescence(50, TimeUnit.SECONDS);
        ForkJoinPool.commonPool().shutdown();

        Thread.sleep(50000);

        log.info("Main exit");

    }

}
