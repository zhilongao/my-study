package com.example.redis.lua;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 11:04
 * @since v1.0.0001
 */
public class LuaScript {

    public static void main(String[] args) {
        JedisUtil instance = JedisUtil.getInstance();
        Jedis jedis = instance.getJedis();
        simply(jedis);
        int count = 10;
        for(int i = 0; i < count; i++){
            limit(jedis);
        }
        jedis.close();
    }

    private static void simply(Jedis jedis) {
        String lua = "return redis.call('set', KEYS[1], ARGV[1])";
        String key = "test:lua:key";
        String value = "this is a lua script test";
        jedis.eval(lua, 1, key, value);
        String getVal = jedis.get(key);
        System.err.println(getVal);
    }



    private static void limit(Jedis jedis) {
        // 传入参数
        String lua =
                // 直接调用incr localhost命令，第一次会返回1，第二次会返回2
                "local num = redis.call('incr', KEYS[1])\n" +
                // 第一次操作，执行tonumber函数，将num转换为数字，若是该数字为1，给localhost设置一个过期时间为10秒，并返回1
                "if tonumber(num) == 1 then\n" +
                "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                "\treturn 1\n" +
                // 若是已经给localhost调用incr超过5次了，直接返回0
                "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                "\treturn 0\n" +
                // 其它情况，返回1
                "else\n" +
                "\treturn 1\n" +
                "end";
        Object result = jedis.evalsha(jedis.scriptLoad(lua), Arrays.asList("localhost"), Arrays.asList("10", "5"));
        System.err.println(result);
    }
}
