package com.titanic.webflux.tobylecture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@Slf4j
public class DeferredResultController {
    Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

    /**
     * DeferredResult를 이용하면 worker 쓰레드를 만들지 않고 비동기 작업을 처리할 수 있다.
     */
    @GetMapping("/dr")
    public DeferredResult<String> callable() throws InterruptedException {
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>();
        results.add(dr);
        return dr;
    }

    @GetMapping("/dr/count")
    public String drCount() {
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drevent(String msg) {
        for (DeferredResult<String> dr : results) {
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }
        return "OK";
    }
}
