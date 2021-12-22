package com.titanic.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableBatchProcessing
public class RemoteService {
    @RestController
    public static class MyController {
        @GetMapping("/service")
        public String rest(@RequestParam("req") String req) throws InterruptedException {
            Thread.sleep(1000); // sleep을 없애면 NettyEventLoop 이용한 비동기 처리 방식이 정상동작. 다만, 2000초로 지정하면 실패하는 경우가 많음.
            log.info("request service1");
            return req + "/service" ;
        }

        @GetMapping("/service2")
        public String rest2(@RequestParam("req") String req) throws InterruptedException {
            Thread.sleep(1000);
            log.info("request service2");
            return req + "/service2" ;
        }
    }

    public static void main(String[] args) {
        // Set system properties for two tomcat.
        System.setProperty("SERVER.PORT","8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
