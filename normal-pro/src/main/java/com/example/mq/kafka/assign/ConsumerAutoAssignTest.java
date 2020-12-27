package com.example.mq.kafka.assign;


import com.example.mq.kafka.util.InitPropsUtil;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: qingshan
 */
public class ConsumerAutoAssignTest {

    public static final String groupId = "simple-test-group-33";

    public static final String topic1 = "simple-test-topic-33";

    public static final String topic2 = "simple-test-topic-55";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonConsumerProps(groupId);
        // 结果 c1 : p0 p1 p2  c2 : p3 p4 （默认）
        // props.put("partition.assignment.strategy","org.apache.kafka.clients.consumer.RangeAssignor");
        // 结果 c1 : p0 p2 p4  c2 : p1 p3
        //props.put("partition.assignment.strategy","org.apache.kafka.clients.consumer.RoundRobinAssignor");
        // 结果 c1 : p2 p3 p4  c2 : p0 p1
        //props.put("partition.assignment.strategy","org.apache.kafka.clients.consumer.StickyAssignor");
        // 两个消费者消费5个分区
        KafkaConsumer<String,String> consumer1 = new KafkaConsumer<String, String>(props);
        KafkaConsumer<String,String> consumer2 = new KafkaConsumer<String, String>(props);
        // 订阅队列
        consumer1.subscribe(Arrays.asList(topic1));
        consumer2.subscribe(Arrays.asList(topic2));
        try {
            while (true){
                ConsumerRecords<String,String> records1 = consumer1.poll(Duration.ofMillis(1000));
                RecordPrintUtil.printRecordMessage("consumer1", records1);
                ConsumerRecords<String,String> records2 = consumer2.poll(Duration.ofMillis(1000));
                RecordPrintUtil.printRecordMessage("consumer2", records2);
            }
        } finally {
            consumer1.close();
        }
    }
}
