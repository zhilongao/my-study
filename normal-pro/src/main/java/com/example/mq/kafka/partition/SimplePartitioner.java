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
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String k = (String) key;
        System.err.println(k);
        if (Integer.parseInt(k) % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
