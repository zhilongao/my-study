package com.example.thread.base.v1;

import java.util.concurrent.CountDownLatch;

/**
 * @author gaozhilong
 */
public class BusinessComputeTask implements Runnable{

    private BusinessComputeNumber business;

    private CountDownLatch latch;

    private boolean safe;

    public BusinessComputeTask(BusinessComputeNumber business, CountDownLatch latch, boolean safe) {
        this.business = business;
        this.latch = latch;
        this.safe = safe;
    }

    @Override
    public void run() {
        try {
            business.add(safe);
        } finally {
            latch.countDown();
        }
    }
}
