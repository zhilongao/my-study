package com.example.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/16 21:19
 * @since v1.0.0001
 */
public class AppV1 {


    public static int count = 0;

    public static void incr() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void add() {
        synchronized (AppV1.class) {
            count ++;
        }
    }


    public static void main(String[] args) {
        int threadNum = 1000;
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> AppV1.incr()).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("count->" + count);
    }
}