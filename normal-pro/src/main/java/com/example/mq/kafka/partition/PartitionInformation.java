package com.example.mq.kafka.partition;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Properties;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 17:29
 * @since v1.0.0001
 */
public class PartitionInformation {

    public static void printTopicInfo(String topic) {
        Properties props = InitPropsUtil.commonProducerProps();
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        List<PartitionInfo> list = producer.partitionsFor(topic);
        for(PartitionInfo part : list){
            System.err.println(part.partition() + "   " + part.leader());
        }
    }
}
