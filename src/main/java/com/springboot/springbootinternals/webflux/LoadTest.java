package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8080/rest";

        // await()을 만나면 대기를 걸고 parties 개수가되면 동시에 쓰레드가 날아간다.
        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                int idx = count.addAndGet(1);

                barrier.await();

                log.info("Thread {} ", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                rt.getForObject(url, String.class);

                sw.stop();
                log.info("Thread : {} | Elapsed: {}", idx, sw.getTotalTimeSeconds());
                return null;
            });
        }

        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("Total elapsed: {}", main.getTotalTimeSeconds());
    }
}
