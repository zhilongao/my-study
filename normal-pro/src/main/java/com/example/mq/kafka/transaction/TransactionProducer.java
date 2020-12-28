package com.example.mq.kafka.transaction;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Properties;
import java.util.UUID;

/**
 * @author gaozhilong
 */
public class TransactionProducer {

    public static final String topic = "transaction-test-topic";

    public static void main(String[] args) {
        produceMessage();
    }

    public static void produceMessage() {
        Properties props = InitPropsUtil.commonProducerProps();
        // 0 发出去就确认 | 1 leader 落盘就确认| all 所有Follower同步完才确认
        // 基于事务的acks必须设置为all
        props.put("acks", "all");
        // 保证消息幂等性
        props.put("enable.idempotence", true);
        // 事务ID，唯一
        props.put("transactional.id", UUID.randomUUID().toString());
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        // 初始化事务
        producer.initTransactions();
        try {
            producer.beginTransaction();
            producer.send(new ProducerRecord<String,String>(topic,"1","1"));
            producer.send(new ProducerRecord<String,String>(topic,"2","2"));
            // Integer i = 1/0;
            producer.send(new ProducerRecord<String,String>(topic,"3","3"));
            // 提交事务
            producer.commitTransaction();
        } catch (KafkaException e) {
            // 中止事务
            producer.abortTransaction();
        }
        producer.close();
    }
}
