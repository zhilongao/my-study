package com.example.mq.rabbitmq.confirm;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gaozhilong
 */
public class NormalConfirmProducer implements Runnable{

    private final static String QUEUE_NAME = "original_queue_1";

    Connection conn;

    Channel channel;

    public NormalConfirmProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "Hello world, Rabbit MQ ,Normal Confirm";
        try {
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 开启发送方确认模式
            channel.confirmSelect();
            // 将消息发送到默认的交换机，routingKey和队列名称相同
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            // 普通Confirm，发送一条，确认一条
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功" );
            } else {
                System.out.println("消息发送失败");
            }
            channel.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
