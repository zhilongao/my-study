package com.study.mq.rocketmq.example;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Set;

public class Consumer {
    public static final String group = "my_test_consumer_group";

    public static void main(String[] args) throws MQClientException {
        // 1.创建消费者
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(group);
        consumer.setNamesrvAddr("192.168.0.139:9876");
        consumer.start();
        // 2.从指定的topic中拉取所有消息队列
        Set<MessageQueue> queues = consumer.fetchSubscribeMessageQueues("test_topic_1");
        for (MessageQueue queue : queues) {
            System.out.println("Consume from the queue: " + queue);
        }
    }

}
