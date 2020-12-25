package com.example.thread.cache;

import java.util.concurrent.CountDownLatch;

/**
 * @author gaozhilong
 */
public class CacheLinePaddingV1 {

    public static long COUNT = 1_0000_0000L;

    private static class T{
        // long 8个子节
        // 缓存行64个子节
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(()->{
            for (int i = 0; i < COUNT; i++) {
                arr[0].x = i;
            }
            latch.countDown();
        });
        Thread t2 = new Thread(()->{
            for (int j = 0; j < COUNT; j++) {
                arr[1].x = j;
            }
            latch.countDown();
        });
        final long start = System.nanoTime();
        t1.start();
        t2.start();
        latch.await();
        System.err.println((System.nanoTime() - start) / 100_0000);
    }


}
