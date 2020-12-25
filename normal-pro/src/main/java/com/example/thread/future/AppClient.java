package com.example.thread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/25 11:27
 * @since v1.0.0001
 */
public class AppClient {
    public static void main(String[] args) {
        // simpleFuture();
        guavaFuture();
    }

    private static void guavaFuture() {
        GuavaFutureTask.simpleTest();
    }

    private static void simpleFuture() {
        SimpleCallableTaskV1 task = new SimpleCallableTaskV1();
        FutureTask futureTask = new FutureTask(task);
        Thread t1 = new Thread(futureTask);
        t1.start();
        for (;;) {
            if (futureTask.isDone()) {
                try {
                    String res = (String)futureTask.get();
                    System.err.println(res);
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            // 任务还未完成，睡一秒再试
            try {
                System.err.println("task is not complete, wait 1 seconds");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
