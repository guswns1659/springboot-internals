package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * subscribeOn : 보통 블락킹 DB 호출 등 publish가 늦어지는 경우에 사용. publish쪽을 다른 쓰레드로 하게끔
 * publishOn : 보통 컨슈머가 느린 경우에 사용. 컨슈머를 다른 쓰레드로 한다.
 */
@Slf4j
public class SchedulerEx {
    public static void main(String[] args) {

        Publisher<Integer> pub = s -> {
            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.info("request()");
                    s.onNext(1);
                    s.onNext(2);
                    s.onNext(3);
                    s.onNext(4);
                    s.onNext(5);
                    s.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

//        Publisher<Integer> subOnPub = sub -> {
//            ExecutorService es = Executors.newSingleThreadExecutor();
//            es.execute(() -> pub.subscribe(sub));
//        };

        Publisher<Integer> pubOnPub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor();

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(()-> sub.onError(t));
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("onNext : {}  ", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError : {}  ", t);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });
        System.out.println("exit");
    }
}
