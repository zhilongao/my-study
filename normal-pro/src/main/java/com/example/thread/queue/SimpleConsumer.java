package com.example.thread.queue;

import java.util.Queue;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 14:39
 * @since v1.0.0001
 */
public class SimpleConsumer implements Runnable {

    private Queue<String> queue;

    private int maxSize;

    public SimpleConsumer(Queue<String> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("消费者消费消息:" + queue.remove());
                queue.notify();
            }
        }
    }
}
