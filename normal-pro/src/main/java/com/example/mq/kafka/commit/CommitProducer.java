package com.example.mq.kafka.commit;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class CommitProducer {

    public static final String topicName = "simple-test-topic1";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        for (int i =0 ; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>(topicName,Integer.toString(i),Integer.toString(i)));
        }
        producer.close();
    }
}
