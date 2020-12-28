package com.example.mq.kafka.idempotence;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: qingshan
 */
public class IdempotenceProducer {
    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        // 消息幂等性
        props.put("enable.idempotence", true);
        // 创建Sender线程
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        int limit = 10;
        for (int i =0 ;i < limit; i++) {
            producer.send(new ProducerRecord<String,String>("mytopic", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }
}
