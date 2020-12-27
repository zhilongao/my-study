package com.example.mq.kafka.partition;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author: qingshan
 */
public class ProducerHandPartition {

    public static final String topic = "simple-test-topic-22";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = InitPropsUtil.commonProducerProps();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        int partitionSize = producer.partitionsFor(topic).size();
        System.out.println("Partition size: " + partitionSize);
        for (int i = 0; i < 10; i++) {
            // 自定义，随机选择分区
            int partition = new Random().nextInt(partitionSize);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, partition, null, i+"");
            RecordMetadata metadata = producer.send(producerRecord).get();
            System.err.println("Sent to partition: " + metadata.partition() + ", offset: " + metadata.offset());
        }
    }

}
