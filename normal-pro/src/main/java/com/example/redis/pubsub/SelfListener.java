package com.example.redis.pubsub;

import redis.clients.jedis.JedisPubSub;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/24 16:53
 * @since v1.0.0001
 */
public class SelfListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.err.println(channel + "=" + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.err.println(pattern + "=" + channel + "=" + message);
    }
}
