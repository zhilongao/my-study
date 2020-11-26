package com.example.redis.pipeline;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/26 19:35
 * @since v1.0.0001
 */
public class PipelineSet {

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        Pipeline pipelined = jedis.pipelined();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipelined.set("batch" + i, ""+i);
        }
        // 批量发送一组命令
        pipelined.syncAndReturnAll();
        long t2 = System.currentTimeMillis();
        System.err.println("耗时:" + (t2 - t1) + "ms");
    }

}
