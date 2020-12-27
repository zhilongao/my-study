package com.example.mq.kafka.assign.singlepart;


import com.example.mq.kafka.util.InitPropsUtil;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.example.mq.kafka.util.RecordPrintUtil.printRecordMessage;

/**
 * @author: qingshan
 */
public class SinglePartitionConsumer {

    public static final String topicName = "simple-test-topic-22";

    public static final String groupId = "simple-test-group1";

    public static void main(String[] args) {
        // 初始化消费者属性
        Properties props = InitPropsUtil.commonConsumerProps(groupId);
        // 1个消费者消费1个分区
        KafkaConsumer<String,String> consumer1 = new KafkaConsumer<String, String>(props);
        KafkaConsumer<String,String> consumer2 = new KafkaConsumer<String, String>(props);
        KafkaConsumer<String,String> consumer3 = new KafkaConsumer<String, String>(props);
        // 订阅队列
        consumer1.subscribe(Arrays.asList(topicName));
        consumer2.subscribe(Arrays.asList(topicName));
        consumer3.subscribe(Arrays.asList(topicName));
        try {
            for (;;){
                ConsumerRecords<String,String> records1 = consumer1.poll(Duration.ofMillis(1000));
                printRecordMessage("consumer1", records1);
                ConsumerRecords<String,String> records2 = consumer2.poll(Duration.ofMillis(1000));
                printRecordMessage("consumer2", records2);
                ConsumerRecords<String,String> records3 = consumer3.poll(Duration.ofMillis(1000));
                printRecordMessage("consumer3", records3);
            }
        } finally {
            consumer1.close();
            consumer2.close();
            consumer3.close();
        }
    }



}
