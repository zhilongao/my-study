package com.example.mq.kafka.assign;


import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: qingshan
 */
public class ProducerSendTest {

    public static final String topic = "simple-test-topic-33";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        Producer<String,String> producer = new KafkaProducer<String,String>(props);

        producer.send(new ProducerRecord<String, String>(topic,0,"0","0"));
        producer.send(new ProducerRecord<String, String>(topic,1,"1","1"));
        producer.send(new ProducerRecord<String, String>(topic,1,"2","2"));
        producer.send(new ProducerRecord<String, String>(topic,1,"3","3"));
        producer.send(new ProducerRecord<String, String>(topic,2,"4","4"));

        producer.close();
    }

}
