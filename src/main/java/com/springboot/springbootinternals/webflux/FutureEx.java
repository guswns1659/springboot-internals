package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws InterruptedException {
        // newCachedThreadPool = maxium 제한이 없고 처음엔 풀에 스레드가 없다. 사용한 후에 반납하면 그대로 가지고 있다.
        ExecutorService es = Executors.newCachedThreadPool();

        /**
         * sumbit : have result of async programming.
         * execute : doesn't have result of async programming.
         * -------
         * callable : Ability to return, and throw exception
         * runnable : Not possible above.
         */
        es.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            log.info("Async");
            return "hello";
        });

        log.info("Exit");
    }
}
