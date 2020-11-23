package com.example.redis.type;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:22
 * @since v1.0.0001
 */
public class StringType {

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.set("qingshan", "2675");
        String val = jedis.get("qingshan");
        System.err.println(val);
    }
}
