package com.charles.im.common;

import com.charles.im.common.utils.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 消息检测
 */
public class MessageCheck {

    public static Map<String, List<String>> cacheMap = new HashMap<>();

    public static Jedis getJedis() {
        return RedisUtils.getJedis();
    }

    public boolean addMessageToQueue(String fromUserId, String toUserId, String message) {
        Jedis jedis = getJedis();
        jedis.zadd(fromUserId + "-" + toUserId, System.currentTimeMillis(), message);
        return true;
    }

    public boolean addMessageToQueueByMemory(String fromUserId, String toUserId, String message) {
        List<String> results = cacheMap.getOrDefault(fromUserId + "-" + toUserId, new ArrayList<String>());
        results.add(message);
        cacheMap.put(fromUserId + "-" + toUserId, results);
        return true;
    }

    public List<Object> pollMessageFromQueue(String fromUserId, String toUserId, int num) {
        Jedis jedis = getJedis();
        List<Object> result = new ArrayList<>();
        String key = fromUserId + "-" + toUserId;
        for (int i = 0; i < num; i++) {
            Set<Tuple> tuple = jedis.zpopmax(key, 1);
            Object[] objects = tuple.toArray();
            if (objects.length > 0) {
                result.add(objects[0]);
            }
        }
        return result;
    }

    public List<Object> pollMessageFromQueueByMemory(String fromUserId, String toUserId, int num) {
        String key = fromUserId + "-" + toUserId;
        List<String> results = cacheMap.getOrDefault(key, new ArrayList<>());
        List<Object> temp = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (!results.isEmpty()) {
                temp.add(results.remove(results.size() - 1));
            }
        }
        return temp;
    }


    public void testRedis() {
        MessageCheck check = new MessageCheck();
        String fromUserId = "u0001";
        String toUserId = "u0002";
        for (int i = 0; i < 100; i++) {
            check.addMessageToQueue(fromUserId, toUserId, "消息" + i);
        }
        for (int i = 0; i < 100; i++) {
            List<Object> results = check.pollMessageFromQueue(fromUserId, toUserId, 1);
            if (results.size() > 0) {
                System.err.println(results.get(0));
            }
        }
    }

    public void testMemory() {
        MessageCheck check = new MessageCheck();
        String fromUserId = "u0001";
        String toUserId = "u0002";
        for (int i = 0; i < 100; i++) {
            check.addMessageToQueueByMemory(fromUserId, toUserId, "消息" + i);
        }
        for (int i = 0; i < 100; i++) {
            List<Object> results = check.pollMessageFromQueueByMemory(fromUserId, toUserId, 1);
            if (results.size() > 0) {
                System.err.println(results.get(0));
            }
        }
    }

    public static void main(String[] args) {
        MessageCheck check = new MessageCheck();
        // check.testMemory();
        check.testRedis();
    }
}
