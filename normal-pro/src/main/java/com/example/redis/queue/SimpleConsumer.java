package com.example.redis.queue;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/21 16:21
 * @since v1.0.0001
 */
public class SimpleConsumer implements Runnable {

    private RedisDelayingQueue<String> queue;

    public SimpleConsumer(RedisDelayingQueue<String> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        queue.loop();
    }
}
