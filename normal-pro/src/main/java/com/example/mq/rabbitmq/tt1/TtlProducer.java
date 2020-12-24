package com.example.mq.rabbitmq.tt1;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author gaozhilong
 */
public class TtlProducer implements Runnable {

    public static final String testTtlChannelName = "test-ttl-exchange";

    public static final String topicExchange = "topic";

    public static final String testTtlQueueName = "test-ttl-queue";

    public static final String routingKey = "ttl-key";

    Connection conn;

    Channel channel;

    public TtlProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "hello,world rabbit mq, dlx msg";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("x-message-ttl", 6000);
        // 对每条消息设置过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                // 持久化消息
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();
        try {
            // 声明交换机 队列 交换机和队列的绑定关系
            channel.exchangeDeclare(testTtlChannelName, topicExchange);
            channel.queueDeclare(testTtlQueueName, false, false, false, paramsMap);
            channel.queueBind(testTtlQueueName, testTtlChannelName, routingKey);
            // 此处两种方式设置过期时间的方式都使用了，将以较小的为准
            channel.basicPublish(testTtlChannelName, routingKey, properties, msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                conn.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
