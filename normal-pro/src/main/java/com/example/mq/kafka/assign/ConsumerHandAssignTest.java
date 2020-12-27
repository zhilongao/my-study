package com.example.mq.kafka.assign;


import com.example.mq.kafka.util.InitPropsUtil;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: qingshan
 */
public class ConsumerHandAssignTest {

    public static final String groupId = "simple-test-group-55";

    public static final String topic = "simple-test-topic-33";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonConsumerProps(groupId);
        KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(props);
        // 订阅topic，消费指定Partition
        TopicPartition tp = new TopicPartition(topic,0);
        consumer.assign(Arrays.asList(tp));
        while (true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            RecordPrintUtil.printRecordMessage("simple-consumer", records);
        }
    }

}
