package com.example.mq.kafka.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 17:36
 * @since v1.0.0001
 */
public class SimplePartitioner implements Partitioner {

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
