package com.example.redis.transaction;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 16:18
 * @since v1.0.0001
 */
public class RedisTransaction {

    public static final String trxKey = "trx-key";

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                Jedis jedis = JedisUtil.getInstance().getJedis();
                String watch = jedis.watch(trxKey);
                System.err.println("method1线程[" + Thread.currentThread().getName() + "]watch结果:" + watch);
                Transaction multi = jedis.multi();
                multi.set(trxKey, "2673-thread1");
                // 让thread2线程优先执行
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Object> exec = multi.exec();
                System.err.println("method1执行结果：" + exec);
                jedis.unwatch();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Jedis jedis = JedisUtil.getInstance().getJedis();
                String watch = jedis.watch(trxKey);
                System.out.println("method2线程["+Thread.currentThread().getName()+"]watch结果：" + watch);
                Transaction multi = jedis.multi();
                multi.set(trxKey, "2673-thread2");
                List<Object> exec = multi.exec();
                System.err.println("method2执行结果:" + exec);
            }
        }.start();
    }
}
