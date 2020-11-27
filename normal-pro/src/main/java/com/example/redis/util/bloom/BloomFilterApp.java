package com.example.redis.util.bloom;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/27 10:52
 * @since v1.0.0001
 */
public class BloomFilterApp {

    public static void main(String[] args) {
        //SelfBloomFilter filter = new GuavaBloomFilter();
        SelfBloomFilter filter = new RedisBloomFilter();
        List<Integer> inDataList = new ArrayList<Integer>();
        List<Integer> judgeDataList = new ArrayList<Integer>();
        for(int i = 0; i < 1000000; i ++) {
            inDataList.add(i);
        }
        for(int j = 1000000; j < 2000000; j ++) {
            judgeDataList.add(j);
        }
        filter.putVal(inDataList);
        filter.containsVal(judgeDataList);
    }
}
