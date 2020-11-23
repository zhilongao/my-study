package com.example.redis;

import com.example.redis.lock.DistLock;
import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:11
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        JedisUtil instance = JedisUtil.getInstance();
        Jedis jedis = instance.getJedis();
        String localKey = "local:lock";
        String requestId1 = UUID.randomUUID().toString();
        String requestId2 = UUID.randomUUID().toString();
        int expireTime = 30000;
        boolean b1 = DistLock.tryGetDistributedLock(jedis, localKey, requestId1, expireTime);
        //boolean b2 = DistLock.tryGetDistributedLock(jedis, localKey, requestId2, expireTime);
        System.err.println("b1 = " + b1);
        //System.err.println("b2 = " + b2);
        boolean b = DistLock.releaseDistributedLock(jedis, localKey, requestId1);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(b);
    }
}
