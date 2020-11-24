package com.example.redis.pubsub;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/24 16:50
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        // publish();
        subscribe();
    }

    public static void publish() {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.publish("channel_java", "this is a message about java");
        jedis.publish("channel_python", "this is a message about python");
    }

    public static void subscribe() {
        JedisPubSub listener = new SelfListener();
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.psubscribe(listener, new String[]{"channel_java"});
    }
}
