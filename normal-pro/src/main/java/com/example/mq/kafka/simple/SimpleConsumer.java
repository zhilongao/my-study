package com.example.mq.kafka.simple;

import com.example.mq.kafka.CommonConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author gaozhilong
 */
public class SimpleConsumer implements Runnable {

    private static final String groupId = "simple-test-group";

    private static final String SIMPLE_TOPIC_NAME = "simple-topic";

    @Override
    public void run() {
        Properties props = initProp();
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(SIMPLE_TOPIC_NAME));
        try {
            for (;;) {
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String,String> record : records) {
                    System.err.printf("offset = %d ,key = %s, value = %s, partition = %s%n" ,record.offset(), record.key(), record.value(), record.partition());
                    System.err.println();
                }
            }
        } finally {
            consumer.close();
        }
    }

    private Properties initProp() {
        Properties props = new Properties();
        // kafka连接地址
        props.put("bootstrap.servers", CommonConstant.bootstrapServers);
        // 消费端groupId
        props.put("group.id", groupId);
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit","true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms","1000");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset","earliest");
        // key和value的反序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
