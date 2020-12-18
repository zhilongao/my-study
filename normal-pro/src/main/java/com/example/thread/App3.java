package com.example.thread;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 15:09
 * @since v1.0.0001
 */
public class App3 {

    public static void main(String[] args) {
        Queue<String> queue = new ArrayBlockingQueue<String>(100);
        int max = 20;
        Thread consumerThread = new Thread(new SimpleConsumer(queue, max));
        Thread producerThread = new Thread(new SimpleProducer(queue, max));
        consumerThread.start();
        producerThread.start();
    }

}
