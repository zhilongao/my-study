package com.example.mq.kafka.partition;

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
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props=new Properties();
        String groupID="gp-test-group";
        //props.put("bootstrap.servers","192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        props.put("bootstrap.servers","192.168.44.160:9092");
        props.put("group.id",groupID);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("acks","-1");
        props.put("retries",3);
        props.put("batch.size",16384);
        props.put("linger.ms",10);
        props.put("buffer.memory",33554432);
        props.put("max.block.ms",5000);

        KafkaProducer<String, Integer> producer = new KafkaProducer<String,Integer>(props);

        String topic = "qs4part2673";
        int partitionSize = producer.partitionsFor(topic).size();
        System.out.println("Partition size: " + partitionSize);


        for (int i = 0; i < 10; i++) {
            // 自定义，随机选择分区
            int partition = new Random().nextInt(partitionSize);
            ProducerRecord<String, Integer> producerRecord = new ProducerRecord<String,Integer>(topic, partition, null, i);
            RecordMetadata metadata = producer.send(producerRecord).get();
            System.out.println("Sent to partition: " + metadata.partition() + ", offset: " + metadata.offset());
        }
    }

}
