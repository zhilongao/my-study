package com.example.mq.rabbitmq.common;

import com.rabbitmq.client.Connection;

public class ConnectionFactory {

    public static Connection createConnection() throws Exception{
        // 创建一个ConnectionFactory,并设置相关的属性值
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost("192.168.43.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 从ConnectionFactory中获取到Connection
        Connection connection = factory.newConnection();
        return connection;
    }
}
