package com.study.project.im.utils;

import redis.clients.jedis.Jedis;

import java.util.Properties;

public class RedisUtils {

    public static Jedis getJedis() {
        Properties props = ResourceLoadUtils.get();
        String host = props.getProperty("app.redis.host");
        String port = props.getProperty("app.redis.port");
        Jedis jedis = new Jedis(host, Integer.parseInt(port));
        return jedis;
    }
}
