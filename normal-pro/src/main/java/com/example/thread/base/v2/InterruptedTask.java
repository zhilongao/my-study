package com.example.thread.base.v2;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class InterruptedTask implements Runnable {

    public boolean stop = false;

    public int a = 0;

    public boolean canInterrupted;

    public int canMethodVersion;

    public InterruptedTask(boolean canInterrupted, int canMethodVersion) {
        this.canInterrupted = canInterrupted;
        this.canMethodVersion = canMethodVersion;
    }

    @Override
    public void run() {
        if (!canInterrupted) {
            System.err.println("not interrupted");
            notInterrupted();
        } else {
            switch (canMethodVersion) {
                case 1:
                    System.err.println("version 1");
                    canInterruptedV1();
                    break;
                case 2:
                    System.err.println("version 2");
                    canInterruptedV2();
                    break;
                case 3:
                    System.err.println("version 3");
                    canInterruptedV3();
                    break;
                case 4:
                    System.err.println("version 4");
                    canInterruptedV4();
                    break;
                default:
                    System.err.println("default version1");
                    canInterruptedV1();
                    break;
            }
        }

    }

    public void notInterrupted() {
        while (!stop) {
            a ++;
        }
    }

    /*****************非主流的线程中断方式********************/
    public void canInterruptedV1() {
        while (!stop) {
            a ++;
            // 此操作可以中断线程
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void canInterruptedV2() {
        while (!stop) {
             a ++;
             new File("");
        }
    }

    public void canInterruptedV3() {
        while (!stop) {
            a ++;
            synchronized (this) {

            }
        }
    }

    public void canInterruptedV4() {
        while (!stop) {
            a ++;
            System.err.println("");
        }
    }

    /*********************其它两种主流的线程中断方式*********************/
    public volatile boolean on = true;

    public void canInterruptedV5() {
        while (on) {
            a ++;
        }
    }

    public void canInterruptedV6() {
        while (!Thread.currentThread().isInterrupted()) {
            a ++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }


}
