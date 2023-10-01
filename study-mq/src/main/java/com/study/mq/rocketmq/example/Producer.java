package com.study.mq.rocketmq.example;


import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class Producer {
    public static void main(String[] args) throws MQClientException {
        String group = "my_test_producer_group";
        DefaultMQProducer producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr("192.168.0.139:9876");
        producer.start();

        for (int i = 0; i < 6; i++) {
            // topic:
            // tags:用于过滤消息
            // keys:索引建,多个用空格隔开,RocketMq可以根据这些索引建快速检索到消息
            // body:消息主体
            try {
                byte[] body = ("RocketMQ "+ String.format("%05d", i)).getBytes();
                Message msg = new Message("q-2-1", "TagA", "2673",  body);
                SendResult sendResult = producer.send(msg, 100000);
                System.out.println(String.format("%05d", i) + " : " + sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
