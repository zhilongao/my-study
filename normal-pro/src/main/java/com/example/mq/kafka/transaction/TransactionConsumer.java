package com.example.mq.kafka.transaction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TransactionConsumer {

    private static void autoComsumer() {
        Properties props = createCommon();
        props.put("group.id", "gp-test-group1");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset", "earliest");
        // key和value的序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        // 事务的隔离级别
        props.put("isolation.level","read_committed");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅topic
        consumer.subscribe(Arrays.asList("transaction-test"));
        try {
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String,String> record : records){
                    System.err.printf("offset = %d ,key =%s, value= %s, partition= %s%n" ,
                            record.offset(), record.key(), record.value(), record.partition());
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static Properties createCommon() {
        Properties props= new Properties();
        props.put("bootstrap.servers","192.168.44.160:9092");
        return props;
    }
}
