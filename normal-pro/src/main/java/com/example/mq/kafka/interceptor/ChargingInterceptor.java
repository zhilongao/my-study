package com.example.mq.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class ChargingInterceptor implements ProducerInterceptor<String, String> {
    /**
     * 发送消息的时候触发
     * @param record
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        System.err.println("1分钱1条消息，不管那么多反正先扣钱");
        return record;
    }

    /**
     * 收到服务端ack的时候触发
     * @param recordMetadata
     * @param e
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        System.err.println("消息被服务端接收啦");
    }

    @Override
    public void close() {
        System.err.println("生产者关闭了");
    }

    /**
     * 用键值对配置的时候触发
     * @param map
     */
    @Override
    public void configure(Map<String, ?> map) {
        System.err.println("configure...");
    }
}
