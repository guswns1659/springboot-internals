package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // newCachedThreadPool = maxium 제한이 없고 처음엔 풀에 스레드가 없다. 사용한 후에 반납하면 그대로 가지고 있다.
        ExecutorService es = Executors.newCachedThreadPool();

        /**
         * sumbit : have result of async programming.
         * execute : doesn't have result of async programming.
         * -------
         * callable : Ability to return, and throw exception
         * runnable : Not possible above.
         */
        Future<String> f = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                log.info("Async");
                return "hello";
            }
        });

        System.out.println(f.isDone());
        log.info("Exit");
        Thread.sleep(2100);
        System.out.println(f.isDone());
        log.info(f.get());

        System.out.println("----------------------------");

        FutureTask<String> ft = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "hello";
        });

        es.execute(ft);

        System.out.println(ft.isDone());
        log.info("Exit");
        Thread.sleep(2100);
        System.out.println(ft.isDone());
        log.info(ft.get());
    }
}
