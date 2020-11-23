package com.example.redis.type;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:42
 * @since v1.0.0001
 */
public class HyperLogLogType {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        float size = 10000;
        for (int i = 0; i < size; i++) {
            jedis.pfadd("hll", "hll-" + i);
        }
        long total = jedis.pfcount("hll");
        System.err.println(String.format("总数统计： %s", total));
        System.err.println(String.format("准确率: %s", (total / size)));
        System.err.println(String.format("误差率: %s", 1- (total / size)));
    }
}
