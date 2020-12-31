package com.example.mq.rabbitmq.send.confirm;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author gaozhilong
 */
public class NormalConfirmProducer implements Runnable{

    private final static String EXCHANGE_NAME = "original_confirm_exchange";

    private final static String EXCHANGE_TOPIC = "topic";

    private final static String QUEUE_NAME = "original_queue_1";

    private final static String ROUTING_KEY = "simple_routing_key";

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
            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TOPIC);
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 将队列和交换机绑定
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            // 开启发送方确认模式
            channel.confirmSelect();
            // 将消息发送到默认的交换机，routingKey和队列名称相同
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
            // 普通Confirm，发送一条，确认一条
            if (channel.waitForConfirms()) {
                System.err.println("消息发送成功" );
            } else {
                System.err.println("消息发送失败");
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
