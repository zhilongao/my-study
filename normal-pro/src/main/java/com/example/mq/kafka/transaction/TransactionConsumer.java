package com.example.mq.kafka.transaction;

import com.example.mq.kafka.util.InitPropsUtil;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author gaozhilong
 */
public class TransactionConsumer {

    private static final String groupId = "simple-test-group";

    private static final String consumeName = "simpleConsume";

    private static final String topic = "transaction-test-topic";

    public static void main(String[] args) {
        consumeMessage();
    }

    public static void consumeMessage() {
        Properties props = InitPropsUtil.commonConsumerProps(groupId);
        // 事务的隔离级别
        props.put("isolation.level", "read_committed");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅topic
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
                RecordPrintUtil.printRecordMessage(consumeName, records);
            }
        } finally {
            consumer.close();
        }
    }
}
