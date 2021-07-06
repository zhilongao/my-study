package com.example.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Test9 {
    public static void main(String[] args) {
        // 串行流 并行流
        // IntStream.range(1, 100).peek(Test9::debug).count();
        // IntStream.range(1, 100).parallel().peek(Test9::debug).count();

        // 实现效果 先并行 在串行
        // 多次调用串行 和并行 以最后一次为准
        /*IntStream.range(1, 100)
                .parallel().peek(Test9::debug)
                .sequential().peek(Test9::debug2)
                .count();*/

        // 并行流使用的线程池ForkJoinPool.commonPool
        // 默认的线程数是当前机器cpu个数
        // 设置某个属性 可以修改线程池线程数
        // IntStream.range(1, 100).parallel().peek(Test9::debug).count();

        // 使用自己的线程池，不使用默认的线程池 可以防止任务阻塞
        ForkJoinPool pool = new ForkJoinPool(2);
        pool.submit(() -> IntStream.range(1, 100).parallel().peek(Test9::debug).count());
        pool.shutdown();

        synchronized (pool) {
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(int i) {
        System.out.println(Thread.currentThread().getName() + " debug " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void debug2(int i) {
        System.err.println("debug " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
