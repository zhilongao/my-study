package com.example.redis.sentine;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/26 19:46
 * @since v1.0.0001
 */
public class JedisSentinelTest {

    private static JedisSentinelPool pool;

    public static JedisSentinelPool createJedisPool() {
        // master的名字是sentinel.conf配置文件里面的名称
        String masterName = "redis-master";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.8.121");
        sentinels.add("192.168.8.122");
        sentinels.add("192.168.8.123");
        GenericObjectPoolConfig poolConfig;
        pool = new JedisSentinelPool(masterName, sentinels);
        return pool;
    }

    public static void main(String[] args) {
        JedisSentinelPool pool = createJedisPool();
        pool.getResource().set("qingshan", "qq" + System.currentTimeMillis());
        System.out.println(pool.getResource().get("qingshan"));
    }
}
