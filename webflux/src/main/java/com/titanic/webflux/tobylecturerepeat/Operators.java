package com.titanic.webflux.tobylecturerepeat;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Operators {

    public static void main(String[] args) {

        /**
         * Design
         * pub -> mapPub -> sub
         */
        List<Integer> integers = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());

        // pub
        Flow.Publisher<Integer> pub = new Flow.Publisher<>() {

            @Override
            public void subscribe(Flow.Subscriber<? super Integer> sub) {
                sub.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        integers.forEach(sub::onNext);
                        sub.onComplete();
                    }

                    @Override
                    public void cancel() {
                    }
                });

            }
        };


        // mapPub, multiply 10
        Flow.Publisher<Integer> mapPub = getMapPub(pub, 0, (a, b) -> a+b);

        // sub
        Flow.Subscriber<Integer> sub = new Flow.Subscriber<>() {

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                log.info("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                log.info("onNext - {}", item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError");
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };

        mapPub.subscribe(sub);
    }

    private static Flow.Publisher<Integer> getMapPub(Flow.Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> bif) {
        return sub -> pub.subscribe(new DelegateSub(sub) {
            int result = init;

            @Override
            public void onNext(Integer item) {
                result = bif.apply(result, item);
            }

            @Override
            public void onComplete() {
                sub.onNext(result);
                sub.onComplete();
            }
        });
    }
}
