package com.example.mq.kafka.simple;

import com.example.mq.kafka.CommonConstant;
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
        Properties prop = CommonUtils.initProducerProps();
        // 创建Sender线程
        Producer<String,String> producer = new KafkaProducer<String,String>(prop);
        int limit = 100;
        for (int i = 0 ;i < limit; i++) {
            String key = Integer.toString(i);
            String value = Integer.toString(i);
            ProducerRecord<String, String> record = new ProducerRecord<>(CommonConstant.SIMPLE_TEST_TOPIC_NAME, key, value);
            producer.send(record);
            try {
                TimeUnit.MICROSECONDS.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.close();
    }
}
