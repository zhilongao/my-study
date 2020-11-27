package com.example.redis.util.bloom;

import com.example.redis.util.bloom.SelfBloomFilter;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/27 10:19
 * @since v1.0.0001
 */
public class GuavaBloomFilter implements SelfBloomFilter {
    /**
     * 预期要插入多少条数据
     */
    private static int size = 1000000;
    /**
     * 期望的误判率
     */
    private static double fpp = 0.01;
    /**
     * 布隆过滤器
     */
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    @Override
    public void putVal(List<Integer> dataList) {
        for (int i = 0; i < dataList.size(); i ++) {
            bloomFilter.put(dataList.get(i));
        }
    }

    @Override
    public void containsVal(List<Integer> dataList) {
        int count = 0;
        int size = dataList.size();
        for(int i = 0; i < dataList.size(); i ++) {
            if (bloomFilter.mightContain(dataList.get(i))) {
                count++;
            }
        }
        System.out.println("总共的误判数:" + count);
        System.out.println("误判率:" + (double)count / size);
    }
}
