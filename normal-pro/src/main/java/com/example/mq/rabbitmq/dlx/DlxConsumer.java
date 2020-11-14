package com.example.mq.rabbitmq.dlx;

import com.example.mq.rabbitmq.common.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DlxConsumer {

    public static void main(String[] args) throws Exception{
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 指定队列的死信交换机
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("x-dead-letter-exchange","GP_DEAD_LETTER_EXCHANGE");
        // 绑定队列
        channel.queueDeclare("GP_ORI_USE_QUEUE", false, false, false, paramsMap);
        // 声明死信交换机和死信队列
        channel.exchangeDeclare("GP_DEAD_LETTER_EXCHANGE", "topic", false, false, false, null);
        channel.queueDeclare("GP_DEAD_LETTER_QUEUE", false, false, false, null);
        // 此处设置routingKey
        channel.queueBind("GP_DEAD_LETTER_QUEUE","GP_DEAD_LETTER_EXCHANGE","#");
        System.out.println(" Waiting for message....");
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "'");
            }
        };
        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume("GP_DEAD_LETTER_QUEUE", true, consumer);
    }

}
