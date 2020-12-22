package com.example.mq.rabbitmq.dlx;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class DlxProducer {

    public static void main(String[] args) throws Exception {
        // 建立连接
        Connection conn = SelfConnectionFactory.createConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();
        String msg = "Hello world, Rabbit MQ, DLX MSG";
        // 设置属性，消息10秒钟过期
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding("UTF-8")
                .expiration("10000") // TTL
                .build();
        // 发送消息
        channel.basicPublish("", "GP_ORI_USE_QUEUE", properties, msg.getBytes());
        channel.close();
        conn.close();
    }
}
