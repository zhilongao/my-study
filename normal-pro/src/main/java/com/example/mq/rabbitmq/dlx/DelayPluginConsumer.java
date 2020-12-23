package com.example.mq.rabbitmq.dlx;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 *  使用延时插件实现的消息投递-消费者
 *  必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 *  先启动消费者
 */
public class DelayPluginConsumer implements Runnable{

    public static final String delayExchange = "delay_exchange";

    public static final String delayExchangeType = "x-delayed-message";

    public static final String delayQueue = "delay_queue";

    public static final String delayRoutingKey = "delay_routing_key";


    private Connection conn;

    private Channel channel;

    public DelayPluginConsumer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Map<String, Object> paramMap = new HashMap<String, Object>(8);
        paramMap.put("x-delayed-type", "direct");
        try {
            // 声明交换机和队列
            channel.exchangeDeclare(delayExchange, delayExchangeType, false,
                    false, paramMap);
            channel.queueDeclare(delayQueue, false,false,false,null);
            // 绑定交换机与队列
            channel.queueBind(delayQueue, delayExchange, delayRoutingKey);
            // 创建消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String msg = new String(body, StandardCharsets.UTF_8);
                    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    System.err.println("收到消息：[" + msg + "]\n接收时间：" +sf.format(new Date()));
                }
            };
            // 开始获取消息
            channel.basicConsume(delayQueue, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}