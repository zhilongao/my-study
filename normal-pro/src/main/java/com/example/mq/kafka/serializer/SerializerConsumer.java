package com.example.mq.kafka.serializer;

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
public class SerializerConsumer {

    private static final String groupId = "gp-ser-group";

    private static final String topic = "ser-topic";

    private static final String consumeName = "ser-simple-consume";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonConsumerProps(groupId);
        // key和value的序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<String, byte[]> consumer=new KafkaConsumer<String, byte[]>(props);
        // 订阅队列
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true){
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, byte[]> record : records){
                    System.err.printf("offset = %d ,key =%s, value= %s, partition= %s%n" ,record.offset(),record.key(), new User(record.value()),record.partition());
                }
            }
        }finally {
            consumer.close();
        }
    }

}
