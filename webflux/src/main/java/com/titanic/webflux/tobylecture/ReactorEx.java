package com.titanic.webflux.tobylecture;

import reactor.core.publisher.Flux;

public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(e -> {
            e.next(1);
            e.next(2);
            e.next(3);
            e.complete();
        }).log()
                .map(e -> e * 10)
                .reduce(0, (a,b) -> a+b)
                .log()
                .subscribe(s -> System.out.println(s));
    }
}
