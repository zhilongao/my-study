package com.example.mq.kafka.interceptor;

import com.example.mq.kafka.CommonConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InterceptorProducer {

    public static final String simpleTestTopicName = "simple-test-topic1";

    public static void main(String[] args) {
        Properties props = initProps();
        // 添加拦截器
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.example.mq.kafka.interceptor.ChargingInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        producer.send(new ProducerRecord<String,String>(simpleTestTopicName,"1","1"));
        producer.send(new ProducerRecord<String,String>(simpleTestTopicName,"2","2"));
        producer.close();
    }

    public static Properties initProps() {
        Properties props= new Properties();
        String bootstrapServers = CommonConstant.bootstrapServers;
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        // 0 发出去就确认 | 1 leader 落盘就确认| all 所有Follower同步完才确认
        props.put("acks","1");
        // 异常自动重试次数
        props.put("retries",3);
        // 多少条数据发送一次，默认16K
        props.put("batch.size",16384);
        // 批量发送的等待时间
        props.put("linger.ms",5);
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送
        props.put("buffer.memory",33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        props.put("max.block.ms",3000);
        return props;
    }
}
