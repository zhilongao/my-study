package com.example.thread;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/19 16:11
 * @since v1.0.0001
 */
public class T02_Guava_Future {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<Integer> listenableFuture = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
