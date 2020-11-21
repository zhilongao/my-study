package com.example.redis.queue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/21 16:57
 * @since v1.0.0001
 */
public class App {

    public static void main(String[] args) {
        String host = "192.168.8.128";
        int port = 6379;
        String password = "123456";
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
        jedisShardInfo.setPassword(password);
        Jedis jedis = new Jedis(jedisShardInfo);
        RedisDelayingQueue<String> queue = new RedisDelayingQueue<>(jedis, "q-demo");
        Thread producer = new Thread(new SimpleProducer(queue));
        Thread consumer = new Thread(new SimpleConsumer(queue));
        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
