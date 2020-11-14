package com.example.mq.rabbitmq.tt1;

import com.example.mq.rabbitmq.common.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

public class TTLProducer {

    public static void main(String[] args) throws Exception {
        // 创建Connection和Channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 创建消息，通过消息的属性设置过期时间
        String msg = "hello,world rabbit mq, dlx msg";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("x-message-ttl", 6000);
        // 声明队列，默认交换机
        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, paramsMap);
        // 对每条消息设置过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                // 持久化消息
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();
        // 此处两种方式设置过期时间的方式都使用了，将以较小的为准
        // 发送消息
        channel.basicPublish("", "TEST_TTL_QUEUE", properties, msg.getBytes());
        // 关闭channel和connection
        channel.close();
        conn.close();
    }
}
