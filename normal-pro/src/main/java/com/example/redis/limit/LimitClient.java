package com.example.redis.limit;

import java.util.UUID;

/**
 * 实现同一个ip访问次数的限制
 *
 * @author gaozhilong
 * @date 2020/12/4 14:09
 * @since v1.0.0001
 */
public class LimitClient {

    public static void main(String[] args) {
        OperateLimit operateLimit = new OperateLimit();
        String userId = UUID.randomUUID().toString();
        String actionKey = "order:del";
        int period = 2000;
        int maxCount = 5;
        int tryLimit = 10;
        for (int i = 0; i < tryLimit; i++) {
            boolean flag = false;
            try {
                flag = operateLimit.isActionAllowedV1(userId, actionKey, period, maxCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println(i + " operate result is " + flag);
        }

    }
}
