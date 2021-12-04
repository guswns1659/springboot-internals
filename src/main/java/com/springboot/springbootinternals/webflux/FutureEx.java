package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class FutureEx {
    interface SuccessCallback {
        void onSuccess(String result);
    }

    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {
        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(@NotNull Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // newCachedThreadPool = maxium 제한이 없고 처음엔 풀에 스레드가 없다. 사용한 후에 반납하면 그대로 가지고 있다.
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask cft = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            if (1==1) throw new RuntimeException("Async Error!!");
            log.info("Async");
            return "hello";
        },      r -> System.out.println(r),
                t -> System.out.println("Error: " + t.getMessage()));

        es.execute(cft);
        es.shutdown();
    }
}
