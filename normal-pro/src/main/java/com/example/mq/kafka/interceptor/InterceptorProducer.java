package com.example.mq.kafka.interceptor;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InterceptorProducer {

    public static final String topic = "simple-test-topic1";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        // 添加拦截器
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.example.mq.kafka.interceptor.ChargingInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        producer.send(new ProducerRecord<String,String>(topic,"1","1"));
        producer.send(new ProducerRecord<String,String>(topic,"2","2"));
        producer.close();
    }
}
