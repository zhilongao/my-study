package com.example.mq.kafka.commit;

import com.example.mq.kafka.CommonConstant;
import com.example.mq.kafka.util.InitPropsUtil;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CommitConsumer {

    public static final String consumerGroupId = "simple-test-group1";

    public static final String consumerTopicName = "simple-test-topic1";

    public static void main(String[] args) {
        consumeMessage();
    }

    public static void consumeMessage() {
        Properties props = InitPropsUtil.commonConsumerProps(consumerGroupId);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅主题
        consumer.subscribe(Arrays.asList(consumerTopicName));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        for (;;) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                RecordPrintUtil.printRecordMessage("simple-consumer", record);
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                // 同步提交
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}
