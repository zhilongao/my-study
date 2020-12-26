package com.example.mq.rabbitmq.ack;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckProducer implements Runnable {

    private static final String queueName = "test-ack-queue";

    private Connection conn;

    private Channel channel;

    public AckProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "test ack message 异常";
        try {
            // 声明队列
            channel.queueDeclare(queueName, false, false, false, null);
            // 发送消息
            for (int i =0; i<5; i++){
                channel.basicPublish("", queueName, null, (msg+i).getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
