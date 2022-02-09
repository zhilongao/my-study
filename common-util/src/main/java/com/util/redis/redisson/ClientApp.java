package com.util.redis.redisson;

import org.redisson.api.*;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ClientApp {

    public static void main(String[] args) {
        test6();
    }

    // 普通键值对操作
    public static void test1() {
        RedissonClient client = RedissonConfig.createClient();
        String key = "key_001";
        RBucket<String> bucket = client.getBucket(key);
        String value = bucket.get();
        if (StringUtils.isEmpty(value)) {
            bucket.set("val_001");
        }
        client.shutdown();
    }

    // hash操作
    public static void test2() {
        RedissonClient client = RedissonConfig.createClient();
        String key = "key_002";
        RMap<Object, Object> bucket = client.getMap(key);
        bucket.put("field1", "val1");
        bucket.put("field2", "val2");
        System.err.println(bucket.get("field1"));
        // client.shutdown();
    }


    public static void test3() {
        // 使用redisson操作map
        RedissonClient client = RedissonConfig.createClient();
        // 使用管道,减少交互次数
        RBatch batch = client.createBatch();
        String simpleKey = "EXPRESS_SUB_TOTAL_ENT";
        String key1 = "numCount";
        String key2 = "userCount";
        batch.getMap(simpleKey).addAndGetAsync(key1, 1);
        batch.getMap(simpleKey).addAndGetAsync(key2, 1);
        batch.execute();
        // 关闭客户端
        client.shutdown();
    }

    public static void test4() {
        RedissonClient client = RedissonConfig.createClient();
        String simpleKey = "USER_SUB_RECORD";
        long userId1 = 10001L;
        long userId2 = 10002L;
        // redisson的bit map操作,一个key,标识多个field为true或者为false
        RBitSet bitSet = client.getBitSet(simpleKey);
        boolean b1 = bitSet.get(userId1);
        boolean b2 = bitSet.get(userId2);
        System.err.println(b1 + "  " + b2);

        // 操作bit
        RBatch batch = client.createBatch();
        batch.getBitSet(simpleKey).setAsync(userId1, Boolean.TRUE);
        batch.execute();
    }

    // 分布式锁
    public static void test5() {
        RedissonClient client = RedissonConfig.createClient();
        String lockKey = "lock";
        RLock lock = client.getLock(lockKey);
        boolean getLock = false;
        try {
            getLock = lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!getLock) {
            System.err.println(Thread.currentThread().getName() + "--未获取到锁");
        } else {
            System.err.println(Thread.currentThread().getName() + "--成功获取到锁");
        }
    }

    // 测试操作set
    public static void test6() {
        String setKey = "set_key_001";
        RedissonClient client = RedissonConfig.createClient();
        // set添加
        /*
        RSet<Object> rSet = client.getSet(setKey);
        if (rSet.isEmpty()) {
            System.err.println("rSet is empty");
        }
        rSet.add("val1");
        rSet.add("val2");
        rSet.add("val3");
        */
        // set获取
        RSet<Object> rSet = client.getSet(setKey);
        Set<Object> results = rSet.readAll();
        for (Object obj : results) {
            System.err.println(obj);
        }
    }



}
