package com.example.redis.type;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:29
 * @since v1.0.0001
 */
public class ZSetType {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        String key = "setKey";
        jedis.zadd(key, 80, "java");
        jedis.zadd(key, 33, "python");
        jedis.zadd(key, 55, "php");
        jedis.zadd(key, 90, "go");
        Set<String> values = jedis.zrangeByScore(key, 80, 90);
        for(String value : values) {
            System.err.println(value);
        }
    }
}
