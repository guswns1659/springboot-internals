package com.titanic.webmvc.completablefuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class CompletableFutureTest {

    @Test
    @DisplayName("thenApply, thenAccept, thenRun")
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

    @Test
    @DisplayName("thenCompose, thenCombine")
    void completableFuture_tutorial2() throws ExecutionException, InterruptedException {

        // thenCompose
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "thenCompose")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "test"));

        assertThat(stringCompletableFuture.get()).isEqualTo("thenComposetest");

        // thenCombine
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> "thenCombine")
            .thenCombine(CompletableFuture.supplyAsync(() -> " hello"), (s1, s2) -> s1 + s2);

        assertThat(stringCompletableFuture1.get()).isEqualTo("thenCombine hello");

        // thenAcceptBoth
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> "Hello")
            .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " world"),
                (s1, s2) -> System.out.println(s1 + s2));

    }

    @Test
    @DisplayName("allof")
    void completableFuture_tutorial3() throws ExecutionException, InterruptedException {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 1001; i++) {
            int finalI1 = i;
            futures.add(CompletableFuture.supplyAsync(() -> finalI1).thenApply(Integer::intValue));
            System.out.println(i);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
    }
}
