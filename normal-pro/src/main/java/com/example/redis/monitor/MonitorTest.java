package com.example.redis.monitor;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

/**
 * 这种方法
 * 1. 影响服务端性能
 * 2. 只能监听一个节点
 *
 * @author gaozhilong
 * @date 2020/11/26 21:04
 * @since v1.0.0001
 */
public class MonitorTest {
    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.monitor(new JedisMonitor() {
            @Override
            public void onCommand(String command) {
                System.out.println("#monitor: " + command);
                // todo 其它处理
            }
        });
    }
}
