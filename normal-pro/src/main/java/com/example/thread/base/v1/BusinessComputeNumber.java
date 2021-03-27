package com.example.thread.base.v1;

import java.util.concurrent.TimeUnit;

/**
 * @author gaozhilong
 */
public class BusinessComputeNumber {

    // private int count = 0;
    private volatile int count = 0;

    public void add(boolean safe) {
        if (!safe) {
            nodeSafeAdd();
        } else {
            saveAdd();
        }
    }

    private void nodeSafeAdd() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++;
    }

    private synchronized void saveAdd() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
