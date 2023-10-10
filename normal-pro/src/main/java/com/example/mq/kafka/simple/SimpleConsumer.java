package com.example.mq.kafka.simple;

import com.example.mq.kafka.CommonConstant;
import com.example.mq.kafka.util.CommonUtils;
import com.example.mq.kafka.util.RecordPrintUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * simple consumer
 * @author gaozhilong
 */
public class SimpleConsumer implements Runnable {

    @Override
    public void run() {
        String topic = CommonConstant.SIMPLE_TEST_TOPIC_NAME;
        Properties props = CommonUtils.initConsumerProps();
        // 更换消费者组,可以重新消费消息
        // props.put("group.id", "my-test-consume");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topic));
        try {
            for (;;) {
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
                RecordPrintUtil.printRecordMessage(CommonConstant.SIMPLE_CONSUMER_NAME, records);
            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        SimpleConsumer task = new SimpleConsumer();
        Thread t = new Thread(task);
        t.start();
    }
}
