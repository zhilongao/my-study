package com.example.mq.kafka.commit;

import com.example.mq.kafka.CommonConstant;
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
        Properties props = initProps();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅主题
        consumer.subscribe(Arrays.asList(consumerTopicName));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        for (;;) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d ,key =%s, value= %s, partition= %s%n" ,
                        record.offset(), record.key(), record.value(), record.partition());
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                // 同步提交
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    public static Properties initProps() {
        Properties props= new Properties();
        String bootstrapServers = CommonConstant.bootstrapServers;
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", consumerGroupId);
        // 是否自动提交偏移量，只有commit之后才更新消费组的offset
        props.put("enable.auto.commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset", "earliest");
        // key和value的反序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
