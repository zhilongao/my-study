package com.example.mq.kafka.simple;

import com.example.mq.kafka.CommonConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author gaozhilong
 */
public class SimpleProducer implements Runnable {

    @Override
    public void run() {
        Properties prop = initProp();
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

    private Properties initProp() {
        Properties prop = new Properties();
        // kafka连接地址
        prop.put("bootstrap.servers", CommonConstant.bootstrapServers);
        // key和value的序列化方式
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        // 确认机制 0:发送出去就确认 1:leader罗盘就确认 all(-1) 所有follower同步完才确认
        prop.put("acks","1");
        // 异常自动重试次数
        prop.put("retries",3);
        // 多少条数据发送一次，默认16k
        prop.put("batch.size",16384);
        // 批量发送的等待时间
        prop.put("linger.ms",5);
        // 客户端缓冲区大小，默认32M 满了也会触发消息发送
        prop.put("buffer.memory",33554432);
        // 获取元数据时生产者的阻塞时间 超时后抛出异常
        prop.put("max.block.ms",3000);
        return prop;
    }
}
