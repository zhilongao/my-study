package com.example.mq.kafka.serializer;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: qingshan
 */
public class SerializerProducer {

    private static final String topic = "ser-topic";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.example.mq.kafka.serializer.ProtobufSerializer");
        Producer<String, User> producer = new KafkaProducer<String,User>(props);
        User user = new User(100L,"qingshan",1,"13677778888");
        producer.send(new ProducerRecord<String, User>(topic,"1", user));
        producer.close();
    }

}
