package com.example.redis.util.bloom;

import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/27 11:11
 * @since v1.0.0001
 */
public interface SelfBloomFilter {

    void putVal(List<Integer> dataList);

    void containsVal(List<Integer> dataList);
}
