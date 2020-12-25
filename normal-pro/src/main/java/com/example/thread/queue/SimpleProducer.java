package com.example.thread.queue;


import java.util.Queue;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 14:36
 * @since v1.0.0001
 */
public class SimpleProducer implements Runnable {

    private Queue<String> queue;

    private int maxSize;

    public int i = 0;

    public SimpleProducer(Queue<String> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            i ++;
            synchronized (queue) {
                while (queue.size() == maxSize) {
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
                String message = "消息" + i;
                System.err.println("生产者生产消息:" + message);
                queue.add(message);
                queue.notify();
            }
        }
    }
}
