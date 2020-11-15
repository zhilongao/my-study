package com.example.mq.rabbitmq.confirm;

import com.example.mq.rabbitmq.common.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 * 普通确认模式
 */
public class NormalConfirmProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE2";

    public static void main(String[] args) throws Exception {
        // 创建消息通道
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        String msg = "Hello world, Rabbit MQ ,Normal Confirm";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 开启发送方确认模式
        channel.confirmSelect();
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        // 普通Confirm，发送一条，确认一条
        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功" );
        }else{
            System.out.println("消息发送失败");
        }
        channel.close();
        conn.close();
    }
}
