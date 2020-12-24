package com.example.mq.rabbitmq.limit;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 * 消息生产者，在启动消费者之后再启动
 * 用于测试消费者限流
 */
public class LimitProducer implements Runnable{

    private final static String QUEUE_NAME = "test_limit_queue";

    Connection conn;

    Channel channel;

    public LimitProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "a limit message ";
        try {
            // 声明队列（默认交换机AMQP default，Direct）
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 发送消息
            int limit = 100;
            for(int i = 0; i < limit; i++){
                channel.basicPublish("", QUEUE_NAME, null, (msg+i).getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}

