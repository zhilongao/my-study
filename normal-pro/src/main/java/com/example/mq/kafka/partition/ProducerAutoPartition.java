package com.example.mq.kafka.partition;

import com.example.mq.kafka.CommonConstant;
import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/17 17:58
 * @since v1.0.0001
 */
public class ProducerAutoPartition {

    public static final String topic = "simple-test-topic-22";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 初始化属性
        Properties props = InitPropsUtil.commonProducerProps();
        // 使用自定义分区器
        props.put("partitioner.class", "com.example.mq.kafka.partition.SimplePartitioner");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        int partitionSize = producer.partitionsFor(topic).size();
        System.err.println("Partition size: " + partitionSize);
        for (int i = 0; i < 10; i++) {
            // 使用自定义分区器选择分区
            // 默认是对key进行hash取余
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, i+ "", i+"");
            RecordMetadata metadata = producer.send(producerRecord).get();
            System.err.println("Sent to partition: " + metadata.partition() + ", offset: " + metadata.offset());
        }
    }

}
