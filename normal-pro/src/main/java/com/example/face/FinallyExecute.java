package com.example.face;

import java.util.concurrent.TimeUnit;

public class FinallyExecute {

    public static void testFinally() {
        FinallyExecute app = new FinallyExecute();
        // 虚拟机的异常退出导致finally中的代码不会被执行
        // app.test1();

        // 由于try块中的死循环导致finally中的代码不会被执行
        // app.test2();

        // 用户线程退出，守护线程也跟着结束(此时守护线程中的代码没执行完就会退出)
        System.err.println("main thread execute before");
        app.test3();
        System.err.println("main thread execute after");
    }


    /**
     * 虚拟机的异常退出导致finally中的代码不会被执行
     */
    public void test1() {
        try {
            System.err.println("exit before");
            System.exit(-1);
            System.err.println("exit after");
        } finally {
            System.err.println("test1 finally");
        }
    }

    /**
     * 由于try块中的死循环导致finally中的代码不会被执行
     */
    public void test2() {
        try {
            System.err.println("loop before");
            while (true) {

            }
        } finally {
            System.err.println("test2 finally");
        }
    }

    /**
     * 用户线程退出，守护线程也跟着结束(此时守护线程中的代码没执行完就会退出)
     */
    public void test3() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.err.println("daemon thread start");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println("daemon thread end");
                } finally {
                    System.err.println("daemon thread finally code");
                }

            }
        });
        t.setDaemon(true);
        t.start();
    }
}
