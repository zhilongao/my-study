package com.example.redis.pipeline;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/26 19:39
 * @since v1.0.0001
 */
public class PipelineGet {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Jedis jedis = JedisUtil.getInstance().getJedis();
                Set<String> keys = jedis.keys("batch*");
                List<String> result = new ArrayList();
                long t1 = System.currentTimeMillis();
                for (String key : keys) {
                    result.add(jedis.get(key));
                }
                for (String src : result) {
                    //System.out.println(src);
                }
                System.err.println("直接get耗时:" + (System.currentTimeMillis() - t1) + "ms");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Jedis jedis = JedisUtil.getInstance().getJedis();
                Set<String> keys = jedis.keys("batch*");
                List<Object> result = new ArrayList();
                Pipeline pipelined = jedis.pipelined();
                long t1 = System.currentTimeMillis();
                for (String key : keys) {
                    pipelined.get(key);
                }
                result = pipelined.syncAndReturnAll();
                for (Object src : result) {
                    //System.out.println(src);
                }
                System.out.println("Pipeline get耗时："+(System.currentTimeMillis() - t1));
            }
        }.start();
    }
}
