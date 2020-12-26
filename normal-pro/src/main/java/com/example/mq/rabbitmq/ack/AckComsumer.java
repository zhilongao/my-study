package com.example.mq.rabbitmq.ack;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;


public class AckComsumer implements Runnable {

    private static final String queueName = "test-ack-queue";

    private Connection conn;

    private Channel channel;

    public AckComsumer() {
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
            // 声明队列
            channel.queueDeclare(queueName, false, false, false, null);
            System.err.println("waiting for message");
            // 创建消费者，消费消息
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.err.println("Received message : '" + msg + "'");
                    if (msg.contains("拒收")){
                        // 拒绝消息
                        // requeue：是否重新入队列，true：是；false：直接丢弃，相当于告诉队列可以直接删除掉
                        // 如果只有这一个消费者，requeue为true的时候会造成消息重复消费
                        System.err.println("消息中包含拒收，直接抛弃");
                        channel.basicReject(envelope.getDeliveryTag(), false);
                    } else if (msg.contains("异常")) {
                        // 批量拒绝
                        // requeue：是否重新入队列
                        // 如果只有这一个消费者，requeue为true的时候会造成消息重复消费
                        System.err.println("消息中包含异常，重新入队");
                        channel.basicNack(envelope.getDeliveryTag(), true, false);
                    } else {
                        // 手工应答
                        // 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
                        channel.basicAck(envelope.getDeliveryTag(), true);
                    }
                }
            };
            // 开始获取消息，此处开启手工应答
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
