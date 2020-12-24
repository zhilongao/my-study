package com.example.mq.rabbitmq.limit;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 * 消息消费者，测试消费端限流，先启动
 */
public class LimitConsumerV1 implements Runnable {

    private final static String QUEUE_NAME = "test_limit_queue";

    Connection conn;

    Channel channel;

    public LimitConsumerV1() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 声明一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.err.println("Consumer1 Waiting for message ....");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String msg = new String(body, StandardCharsets.UTF_8);
                    System.err.println("Consumer1 Received message : '" + msg + "'" );
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            };
            // 非自动确认消息的前提下，如果一定数目的消息（通过基于consume或者channel设置Qos的值）未被确认前，不进行消费新的消息。
            channel.basicQos(2);
            channel.basicConsume(QUEUE_NAME, false, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

