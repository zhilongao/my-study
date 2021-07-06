package com.example.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Test1 {
    public static void main(String[] args) {
        // Netty
        // Servlet3.1
        String[] strs = {"1", "3", "5"};

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.err.println("接受到数据:" + integer);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("完毕!");
            }
        };

        Flux.fromArray(strs).map(p -> Integer.parseInt(p)).subscribe(subscriber);

    }
}
