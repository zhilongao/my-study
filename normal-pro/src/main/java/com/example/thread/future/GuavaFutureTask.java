package com.example.thread.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/25 11:51
 * @since v1.0.0001
 */
public class GuavaFutureTask {

    public static void simpleTest() {
        SimpleCallableTaskV1 task = new SimpleCallableTaskV1();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<String> listenableFuture = service.submit(task);
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("get listenable future's result with callback " + result);
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        // 关闭线程池，任务执行完该线程池结束
        service.shutdown();
    }
}
