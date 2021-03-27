package com.example.thread.base.v1;

import java.util.concurrent.CountDownLatch;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/16 21:19
 * @since v1.0.0001
 */
public class ComputeApp {


    public static void main(String[] args) {
        int testNum = 10;
        for (int i = 0; i < testNum; i++) {
            simpleTest(true);
        }
        System.err.println("\n--------------------");
        for (int i = 0; i < testNum; i ++) {
            simpleTest(false);
        }
        System.err.println("\n--------------------");
    }

    public static void simpleTest(boolean safe) {
        int threadNum = 1000;
        BusinessComputeNumber business = new BusinessComputeNumber();
        CountDownLatch latch = new CountDownLatch(threadNum);
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i ++) {
            threads[i] = new Thread(new BusinessComputeTask(business, latch, safe));
        }
        for (int i = 0; i < threadNum; i ++) {
            threads[i].start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.print("count->" + business.getCount() + "\t");
    }
}
