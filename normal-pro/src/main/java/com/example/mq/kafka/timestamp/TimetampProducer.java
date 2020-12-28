package com.example.mq.kafka.timestamp;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: qingshan
 */
public class TimetampProducer {

    private static final String topic = "test-timestamp-topic";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        // 创建Sender线程
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        producer.send(new ProducerRecord<String,String>(topic,"1","1a"));
        producer.send(new ProducerRecord<String,String>(topic,"2","2a"));
        producer.send(new ProducerRecord<String,String>(topic,"3","3a"));
        producer.send(new ProducerRecord<String,String>(topic,"4","4a"));
        producer.close();
    }
}
