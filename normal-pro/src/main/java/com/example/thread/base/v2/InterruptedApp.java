package com.example.thread.base.v2;

import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 19:39
 * @since v1.0.0001
 */
public class InterruptedApp {

    public static void main(String[] args) {
        boolean canInterrupted = true;
        int canMethodVersion = 4;
        InterruptedTask task = new InterruptedTask(canInterrupted, canMethodVersion);
        Thread t = new Thread(task);
        t.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.stop = true;
    }

}
