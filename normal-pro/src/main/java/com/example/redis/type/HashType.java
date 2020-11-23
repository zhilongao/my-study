package com.example.redis.type;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:24
 * @since v1.0.0001
 */
public class HashType {

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.hset("h1", "name", "jack");
        jedis.hset("h1", "age", "18");
        jedis.hset("h1", "gender", "男");

        Map<String, String> h1 = jedis.hgetAll("h1");
        Set<Map.Entry<String, String>> entries = h1.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.err.println(entry.getKey() + "--" + entry.getValue());
        }
    }

}
