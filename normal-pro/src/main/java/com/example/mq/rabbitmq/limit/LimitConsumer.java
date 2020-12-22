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
public class LimitConsumer {

    private final static String QUEUE_NAME = "test_limit_queue";

    public static void main(String[] args) throws Exception {
        // 建立连接
        Connection conn = SelfConnectionFactory.createConnection();
        // 创建消息通道
        final Channel channel = conn.createChannel();
        // 声明队列（默认交换机AMQP default，Direct）
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.err.println("Consumer1 Waiting for message ....");
        // 创建消费者，并接收消息
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
    }
}

