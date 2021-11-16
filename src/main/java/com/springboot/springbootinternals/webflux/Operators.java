package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Operators
 * <p>
 * pub -> [data1] -> mapPub -> [data2] -> logSub
 * 1. map (d1 -> f -> d2)
 */
@Slf4j
public class Operators {
    public static void main(String[] args) {
        // publisher that publish 1 to 10 sequentially.
        Flow.Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        Flow.Publisher<Integer> mapPub = mapPub(pub, a -> a * 10);
//        Flow.Publisher<Integer> reducePub = reducePub(pub, 0, (BiFunction<Integer, Integer, Integer>) (a, b) -> a + b);
        mapPub.subscribe(logSub());
    }

    /** https://www.youtube.com/watch?v=DChIxy9g19o&list=PLv-xDnFD-nnmof-yoZQN8Fs2kVljIuFyC&index=9
     *  Operators 뒷부분이 Generic으로 변환하는 과정인데 하다가 어려워서 우선 주석처리하고 넘어감
     */
//    private static Flow.Publisher<Integer> reducePub(Flow.Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> bif) {
//        return new Flow.Publisher<Integer>() {
//            @Override
//            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
//
//                pub.subscribe(new DelegateSub(subscriber) {
//                    int result = init;
//
//                    @Override
//                    public void onNext(Integer item) {
//                        result = bif.apply(result, item);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        subscriber.onNext(result);
//                        subscriber.onComplete();
//                    }
//                });
//
//            }
//        };
//    }

    private static <T> Flow.Publisher<T> mapPub(Flow.Publisher<T> pub, Function<T, T> f) {
        return new Flow.Publisher<T>() {
            @Override
            public void subscribe(Flow.Subscriber<? super T> subscriber) {
                pub.subscribe(new DelegateSub<T>(subscriber) {
                    @Override
                    public void onNext(T item) {
                        subscriber.onNext(f.apply(item));
                    }
                });
            }
        };
    }

    private static <T> Flow.Subscriber<T> logSub() {
        return new Flow.Subscriber<T>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                log.info("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T item) {
                log.info("onNext : {}", item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError:{}", throwable);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };
    }

    private static Flow.Publisher<Integer> iterPub(Iterable<Integer> iter) {
        return new Flow.Publisher<Integer>() {
            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(subscriber::onNext);
                            subscriber.onComplete();
                        } catch (Throwable t) {
                            subscriber.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };
    }
}
