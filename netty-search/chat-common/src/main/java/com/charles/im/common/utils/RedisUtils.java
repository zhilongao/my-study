package com.charles.im.common.utils;

import io.netty.util.internal.StringUtil;
import redis.clients.jedis.Jedis;

import java.util.Properties;

public class RedisUtils {

    public static Jedis getJedis() {
        Properties props = ResourceLoadUtils.get();
        String host = props.getProperty("app.redis.host");
        String port = props.getProperty("app.redis.port");
        String pass = props.getProperty("app.redis.pass");
        Jedis jedis = new Jedis(host, Integer.parseInt(port));
        if (!StringUtil.isNullOrEmpty(pass)) {
            jedis.auth(pass);
        }
        return jedis;
    }
}
