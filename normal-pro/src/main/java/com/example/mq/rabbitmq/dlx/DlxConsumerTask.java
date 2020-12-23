package com.example.mq.rabbitmq.dlx;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaozhilong
 */
public class DlxConsumerTask implements Runnable{

    public static final String deadLetterExchange = "gp_dead_letter_exchange";

    public static final String dealLetterQueue = "gp_dead_letter_queue";

    public static final String topicExchangeType = "topic";

    public static final String originQueueName = "gp_ori_use_queue";


    public Connection conn;

    public Channel channel;

    public DlxConsumerTask() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 指定队列的死信交换机
            Map<String, Object> paramsMap = new HashMap<String, Object>(8);
            paramsMap.put("x-dead-letter-exchange", deadLetterExchange);
            // 声明原始队列，参数指定绑定的死信交换机
            channel.queueDeclare(originQueueName, false, false, false, paramsMap);
            // 声明死信交换机和死信队列，并将死信交换机和死信队列绑定
            channel.exchangeDeclare(deadLetterExchange, topicExchangeType, false, false, false, null);
            channel.queueDeclare(dealLetterQueue, false, false, false, null);
            channel.queueBind(dealLetterQueue, deadLetterExchange,"#");
            System.out.println(" Waiting for message....");
            // 创建消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String msg = new String(body, StandardCharsets.UTF_8);
                    System.err.println("Received message : '" + msg + "'");
                }
            };
            // 开始获取消息
            channel.basicConsume(dealLetterQueue, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常，直接关闭连接
            try {
                channel.close();
                conn.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}
