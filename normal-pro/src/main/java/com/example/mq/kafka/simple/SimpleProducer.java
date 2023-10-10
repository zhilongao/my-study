package com.example.mq.kafka.simple;

import com.example.mq.kafka.CommonConstant;
import com.example.mq.kafka.topic.TopicOperate;
import com.example.mq.kafka.util.CommonUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * simple producer
 * @author gaozhilong
 */
public class SimpleProducer implements Runnable {

    @Override
    public void run() {
        String topic = CommonConstant.SIMPLE_TEST_TOPIC_NAME;
        // 先创建主题
        TopicOperate.createTopic(topic, 3, (short)2);
        // 创建Sender线程
        Properties prop = CommonUtils.initProducerProps();
        Producer<String,String> producer = new KafkaProducer<String,String>(prop);
        // 发送消息
        int limit = 100;
        for (int i = 0 ;i < limit; i++) {
            sendMessage(producer, topic, String.valueOf(i), String.valueOf(i));
            try {
                TimeUnit.MICROSECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.close();
    }


    public void sendMessage(Producer<String,String> producer, String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
    }

    public static void main(String[] args) {
        // 测试消息发送
        SimpleProducer task = new SimpleProducer();
        Thread t = new Thread(task);
        t.start();
    }

}
