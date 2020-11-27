package com.example.redis.util.bloom;

import com.example.redis.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.util.Hashing;

import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/27 10:32
 * @since v1.0.0001
 */
public class RedisBloomFilter implements SelfBloomFilter {
    /**
     * 期望要插入的数据
     */
    private static int size = 1000;
    /**
     * 期望的误判率
     */
    private static double fpp = 0.01;
    /**
     * bit数组的长度
     */
    private static long bitLen;
    /**
     * hash函数数量
     */
    private static int hashNum;

    static {
        bitLen = optimalNumOfBits(size, fpp);
        hashNum = optimalNumOfHashFunctions(size, bitLen);
    }

    @Override
    public void putVal(List<Integer> dataList) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        for (int i = 0; i < dataList.size(); i ++) {
            Integer num = dataList.get(i);
            long[] indexs = getIndexArray(String.valueOf(num));
            for (long index : indexs) {
                jedis.setbit("codebear:bloom", index, true);
            }
        }
    }

    @Override
    public void containsVal(List<Integer> dataList) {
        Jedis jedis = JedisUtil.getInstance().getJedis();
        int count = 0;
        int size = dataList.size();
        for(int i = 0; i < dataList.size(); i ++) {
            Integer num = dataList.get(i);
            long[] indexs = getIndexArray(String.valueOf(num));
            for (long index : indexs) {
                if (jedis.getbit("codebear:bloom", index)) {
                    count ++;
                }
            }
        }
        System.out.println("总共的误判数:" + count);
        System.out.println("误判率:" + (double)count / size);
    }

    /**
     * 根据key获取bitmap下标
     * @param key key
     * @return
     */
    private static long[] getIndexArray(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[hashNum];
        for (int i = 0; i < hashNum; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % bitLen;
        }
        return result;
    }

    /**
     * 给key返回个下标
     * @param key
     * @return
     */
    private static long hash(String key) {
        return Hashing.MURMUR_HASH.hash(key);
    }

    /**
     * 计算hash函数的数量
     * @param n 期望插入的数据量
     * @param m 误判率
     * @return hash函数的数量
     */
    private static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    /**
     * 计算bit数组的长度
     * @param n 期望插入的数据量
     * @param p 误判率
     * @return bit数组的长度
     */
    private static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

}
