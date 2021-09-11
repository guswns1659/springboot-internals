package com.springboot.springbootinternals.completablefuture;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest {

    @Test
    void completableFuture_tutorial() throws ExecutionException, InterruptedException {

        // thenApply = Function<T, R># R apply(T t)
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Integer> future = completableFuture.thenApply(String::length);
        assertThat(future.get()).isEqualTo(5);

        // thenAccept = consumer<R>#accept(T t)
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture
            .supplyAsync("consumer"::length);
        CompletableFuture<Void> voidCompletableFuture = integerCompletableFuture.thenAccept(System.out::println);
        voidCompletableFuture.get();
        System.out.println(">>>>>" + Thread.currentThread());

        // thenRun = Runnable#run()
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "Runnable");
        CompletableFuture<Void> voidCompletableFuture1 = stringCompletableFuture
            .thenRun(() -> System.out.println(">>>>>" + Thread.currentThread()));

        voidCompletableFuture1.get();
    }
}
