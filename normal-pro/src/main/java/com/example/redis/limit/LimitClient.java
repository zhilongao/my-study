package com.example.redis.limit;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * 实现同一个ip访问次数的限制
 *
 * @author gaozhilong
 * @date 2020/12/4 14:09
 * @since v1.0.0001
 */
public class LimitClient {

    /**
     * 简单限流 通过incr实现
     * @param userId
     * @param actionKey
     * @param period
     * @param maxCount
     * @return
     */
    public boolean isActionAllowedV1(String userId, String actionKey, int period, int maxCount) {
        String key = String.format("hist:%s:%s", userId, actionKey);
        String lua = "local num = redis.call('incr', KEYS[1])\n" +
                "if tonumber(num) == 1 then\n" +
                "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                "\treturn 1\n" +
                "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                "\treturn 0\n" +
                "else\n" +
                "\treturn 1\n" +
                "end";
        Jedis jedis = JedisUtil.getInstance().getJedis();
        Object result = jedis.evalsha(jedis.scriptLoad(lua), Arrays.asList(key), Arrays.asList(String.valueOf(period), String.valueOf(maxCount)));
        return "1".equals(result.toString());
    }

    /**
     * 简单限流 通过zset实现
     * @param userId
     * @param actionKey
     * @param period
     * @param maxCount
     * @return
     * @throws IOException
     */
    public boolean isActionAllowedV2(String userId, String actionKey, int period, int maxCount) throws IOException {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        // 开启一个事务
        pipeline.multi();
        // 1. 在zset中添加一个member，其score的值为nowTs
        pipeline.zadd(key, nowTs, "" + nowTs);
        // 2. 移除那些过期的操作行为(member)
        pipeline.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 3. 获取下key中当前的member数
        Response<Long> count = pipeline.zcard(key);
        // 4. 将key的过期时间延长
        pipeline.expire(key, period + 1);
        // 5. 执行redis的事务
        pipeline.exec();
        pipeline.close();
        // 6. 判断是否需要执行操作
        return count.get() <= maxCount;
    }
}
