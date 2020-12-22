package com.example.mq.rabbitmq;

import com.rabbitmq.client.Connection;

/**
 * rabbitmq链接工厂，创建rabbitmq链接并返回
 */
public class SelfConnectionFactory {

    public static final String host = "192.168.8.128";

    public static final int port = 5672;

    public static final String virtualHost = "/";

    public static final String userName = "guest";

    public static final String passWord = "guest";

    public static Connection createConnection() throws Exception{
        // 创建一个ConnectionFactory,并设置相关的属性值
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        factory.setUsername(userName);
        factory.setPassword(passWord);
        // 从ConnectionFactory中获取到Connection
        Connection connection = factory.newConnection();
        return connection;
    }
}
