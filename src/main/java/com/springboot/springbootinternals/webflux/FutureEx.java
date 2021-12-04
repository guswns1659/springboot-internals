package com.springboot.springbootinternals.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class FutureEx {
    interface SuccessCallback {
        void onSuccess(String result);
    }

    public static class CallbackFutureTask extends FutureTask<String> {
        SuccessCallback sc;

        public CallbackFutureTask(@NotNull Callable<String> callable, SuccessCallback sc) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
        }

        @SneakyThrows
        @Override
        protected void done() {
            sc.onSuccess(get());
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // newCachedThreadPool = maxium 제한이 없고 처음엔 풀에 스레드가 없다. 사용한 후에 반납하면 그대로 가지고 있다.
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask cft = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "hello";
        }, System.out::println);

        es.execute(cft);
        es.shutdown();
    }
}
