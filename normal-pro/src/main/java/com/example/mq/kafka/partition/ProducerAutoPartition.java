package com.example.mq.kafka.partition;

import com.example.mq.kafka.CommonConstant;
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

    private static final String bootstrapServers = CommonConstant.bootstrapServers;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("acks","-1");
        props.put("retries",3);
        props.put("partitioner.class", "com.qingshan.partition.SimplePartitioner");
        props.put("batch.size",16384);
        props.put("linger.ms",10);
        props.put("buffer.memory",33554432);
        props.put("max.block.ms",5000);

        KafkaProducer<String, Integer> producer = new KafkaProducer<String,Integer>(props);
        String topic = "qs4part2673";
        int partitionSize = producer.partitionsFor(topic).size();
        System.err.println("Partition size: " + partitionSize);
        for (int i = 0; i < 10; i++) {
            // 使用自定义分区器选择分区
            // 默认是对key进行hash取余
            ProducerRecord<String, Integer> producerRecord = new ProducerRecord<String,Integer>(topic, i+"", i);
            RecordMetadata metadata = producer.send(producerRecord).get();
            System.out.println("Sent to partition: " + metadata.partition() + ", offset: " + metadata.offset());
        }
    }
}
