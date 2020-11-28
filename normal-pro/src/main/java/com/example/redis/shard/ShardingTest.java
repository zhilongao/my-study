package com.example.redis.shard;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/28 11:08
 * @since v1.0.0001
 */
public class ShardingTest {

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisShardInfo shardInfo1 = new JedisShardInfo("192.168.8.128", 6379);
        JedisShardInfo shardInfo2 = new JedisShardInfo("192.168.8.129", 6379);
        JedisShardInfo shardInfo3 = new JedisShardInfo("192.168.8.132", 6379);
        List<JedisShardInfo> jedisShardInfos = Arrays.asList(shardInfo1, shardInfo2, shardInfo3);
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, jedisShardInfos);
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            for(int i = 0; i < 100; i ++) {
                jedis.set("k" + i, "" + i);
            }
            for (int i = 0; i < 100; i++) {
                System.err.println(jedis.get("k" + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
