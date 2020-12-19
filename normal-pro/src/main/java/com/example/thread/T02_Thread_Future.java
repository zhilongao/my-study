package com.example.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/19 9:56
 * @since v1.0.0001
 */
public class T02_Thread_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // v1();
        v2();
    }

    public static void v1() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        System.err.println(future.get());
        System.err.println("finish");
        service.shutdown();
    }

    public static void v2() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        for (;;) {
            if (future.isDone()) {
                System.err.println(future.get());
                System.err.println("finish");
                break;
            }
            Thread.sleep(1000);
        }
        service.shutdown();
    }


}
