package com.springboot.springbootinternals;

import com.springboot.springbootinternals.kafka.JackProducer;
import com.springboot.springbootinternals.kafka.JackProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * 쓰레드가 많이 만들어지면?
 * - OOME가 발생할 수 있다. 쓰레드가 가져야 하는 기본적인 프로퍼티가 있기 때문에
 * - 컨텍스트 스위칭이 많이 발생할 수 있다. 놀고 있는 쓰레드를 보면 waiting으로 바꾸고
 *   다시 일 할때는 running으로 하기 때문에
 */
@ComponentScan(
                excludeFilters = {
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JackProducer.class, JackProducerConfig.class}),
                @Filter(type = FilterType.REGEX, pattern ={"com.springboot.springbootinternals.rabbitmq.*"})}
)
@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@EnableAsync
public class SpringbootInternalsApplication {

    RestTemplate rt = new RestTemplate();

    /** config 셋팅.
     * server:
     *   tomcat:
     *     threads:
     *       max: 1
     */
    @RestController
    public class MyController {
        @GetMapping("/rest")
        public String rest(@RequestParam("idx") int idx) {
            String res = rt.getForObject("http://localhost:8081/service?req={req}", String.class, "hello " + idx);
            return res;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInternalsApplication.class, args);
    }
}
