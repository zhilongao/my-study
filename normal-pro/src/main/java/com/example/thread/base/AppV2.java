package com.example.thread.base;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 19:39
 * @since v1.0.0001
 */
public class AppV2 {

    public static boolean stop = false;

    public static int a = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    a++;
                    // new File("");
                    /*synchronized (App4.class) {

                    }*/
                    // System.err.println("");
                    /*try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
    }

}
