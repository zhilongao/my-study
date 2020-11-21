package com.example.redis.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/21 16:14
 * @since v1.0.0001
 */
public class RedisDelayingQueue<T>{

    private Type taskType = new TypeReference<TaskItem<T>>() {}.getType();

    private Jedis jedis;

    private String queueKey;

    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<T>();
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        String member = JSON.toJSONString(task);
        long score = System.currentTimeMillis() + 5000;
        jedis.zadd(queueKey, score, member);
    }

    public void loop() {
        while (!Thread.interrupted()) {
            long min = 0;
            long max = System.currentTimeMillis();
            Set<String> values = jedis.zrangeByScore(queueKey, min, max, 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String message = values.iterator().next();
            // 先抢一下
            if (jedis.zrem(queueKey, message) > 0) {
                // fast json反序列化
                TaskItem<T> task = JSON.parseObject(message, taskType);
                this.handleMsg(task.msg);
            }
        }
    }

    public void handleMsg(T msg) {
        System.err.println("deal message " + msg);
    }


    static class TaskItem<T> {
        private String id;
        private T msg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public T getMsg() {
            return msg;
        }

        public void setMsg(T msg) {
            this.msg = msg;
        }
    }
}
