package com.util.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestMain {

    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {

        run();
        Thread.sleep(1000);
        run();
        Thread.sleep(1000);
        run();
        Thread.sleep(1000);
        run();
        Thread.sleep(1000);
        run();

    }


    private static void run() {
        executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(() -> {
                HttpRequest request = HttpRequest.get("http://10.241.1.37:8080/request/run");
                System.out.println("发起请求：" + count.getAndIncrement());
                HttpResponse execute = request.execute();
                String body = execute.body();
                System.out.println(body);
            });
        }
        executorService.shutdownNow();
    }

    static ExecutorService executorService;

}
