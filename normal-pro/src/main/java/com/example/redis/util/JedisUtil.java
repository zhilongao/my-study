package com.example.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 9:52
 * @since v1.0.0001
 */
public class JedisUtil {

    private JedisPool pool;

    private static JedisUtil jedisUtil = null;

    private JedisUtil() {
        if (pool == null) {
            String ip = ResourceUtil.getKey("redis.host");
            Integer port = Integer.valueOf(ResourceUtil.getKey("redis.port"));
            String password = ResourceUtil.getKey("redis.password");
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            int timeout = 10000;
            if (null != password && !"".equals(password)) {
                // redis设置密码的情况
                pool = new JedisPool(jedisPoolConfig, ip, port, timeout, password);
            } else {
                // redis未设置密码的情况
                pool = new JedisPool(jedisPoolConfig, ip, port, timeout);
            }
        }
    }

    /**
     * 获取到JedisUtil对象
     * @return
     */
    public static JedisUtil getInstance() {
        if (jedisUtil == null) {
            synchronized (JedisUtil.class) {
                if (jedisUtil == null) {
                    jedisUtil = new JedisUtil();
                }
            }
        }
        return jedisUtil;
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public String get(String key) {
        Jedis jedis = getJedis();
        return jedis.get(key);
    }


}
