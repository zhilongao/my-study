package com.example.redis.queue;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/21 16:22
 * @since v1.0.0001
 */
public class SimpleProducer implements Runnable {

    private RedisDelayingQueue<String> queue;

    public SimpleProducer(RedisDelayingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int consumeCount = 10;
        for (int i = 0; i < consumeCount; i++) {
            queue.delay("producer message " + i);
        }
    }
}
