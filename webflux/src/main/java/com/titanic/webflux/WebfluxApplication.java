package com.titanic.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    public static final String URL1 = "http://localhost:8081/service?req={req}";
    public static final String URL2 = "http://localhost:8081/service2?req={req}";

    @RestController
    class WebfluxController {

        /**
         * Mono는 List와 같은 컨테이너라 생각하면된다. 컨테이너에 담아두면 컨테이너의 기능을 사용할 수 있다.
         */
        @GetMapping("/rest")
        public Mono<String> rest(@RequestParam("idx") int idx) {
            WebClient webClient = WebClient.create();
            Mono<ClientResponse> response = webClient.get().uri(URL1, idx).exchangeToMono(Mono::just);
            return Mono.just("rest");
        }
    }

}
