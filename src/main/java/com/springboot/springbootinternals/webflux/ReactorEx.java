package com.springboot.springbootinternals.webflux;

import reactor.core.publisher.Flux;

public class ReactorEx {
    public static void main(String[] args) {
        Flux.create(e -> {
            e.next(1);
            e.next(2);
            e.next(3);
            e.complete();
        }).log().subscribe(s -> System.out.println(s));
    }
}
