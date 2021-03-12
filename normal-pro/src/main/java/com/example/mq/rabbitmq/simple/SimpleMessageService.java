package com.example.mq.rabbitmq.simple;

import com.alibaba.fastjson.JSONObject;
import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author gaozhilong
 */
public class SimpleMessageService {

    public static final String SIMPLE_EXCHANGE_NAME = "simple_exchange";

    public static final String EXCHANGE_TYPE_DIRECT = "direct";

    public static final String SIMPLE_ROUTING_KEY_1 = "simple.test";

    public static final String SIMPLE_RECEIVE_QUEUE_NAME = "simpleQueue";

    public static final String ORIGIN_QUEUE_NAME = "originalQueue";

    /**
     * 原生java api生产rabbit mq消息
     * @throws Exception
     */
    public static void simpleProduceMessage(String message) throws Exception{
        Connection connection = SelfConnectionFactory.createConnection();
        // 通过Connection创建消息通道
        Channel channel = connection.createChannel();
        // 通过channel发送消息 需要指定exchange routingKey BasicProperties byte[] body
        String exchangeName = SIMPLE_EXCHANGE_NAME;
        String routingKey = SIMPLE_ROUTING_KEY_1;
        JSONObject messageJson = new JSONObject();
        messageJson.put("content", message);
        messageJson.put("type", "simple");
        channel.basicPublish(exchangeName, routingKey, null, messageJson.toJSONString().getBytes());
        // 关闭channel和connection
        channel.close();
        connection.close();
    }

    /**
     * 原生java api消费 rabbit mq 消息
     * @throws Exception
     */
    public static void simpleConsumerMessage() throws Exception{
        Connection connection = SelfConnectionFactory.createConnection();
        Channel channel = connection.createChannel();
        // 声明交换机的名称和类型(直连)
        String exchangeName = SIMPLE_EXCHANGE_NAME;
        String exchangeType = EXCHANGE_TYPE_DIRECT;
        // 声明channel绑定的交换机(交换机的名称, 交换机的类型 交换机是否持久化 交换机不用的情况下是否删除 一些其它的参数)
        channel.exchangeDeclare(exchangeName, exchangeType, false, false, null);
        // 声明队列
        String queueName = SIMPLE_RECEIVE_QUEUE_NAME;
        channel.queueDeclare(queueName, false, false, false, null);
        // 绑定队列和交换机
        String routingKey = SIMPLE_ROUTING_KEY_1;
        channel.queueBind(queueName, exchangeName, routingKey);
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.err.println("received message : " + msg);
                System.out.println("consumerTag : " + consumerTag );
                System.out.println("deliveryTag : " + envelope.getDeliveryTag() );
            }
        };
        // 开始获取消息 autoAck
        channel.basicConsume(queueName, true, consumer);
    }

    /**
     * 创建默认绑定交换机的生产者
     * @throws Exception
     */
    public static void defaultProduceMessage(String message) throws Exception{
        // 创建Connection和Channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 创建headers(自定义属性)
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("name", "gupao");
        headers.put("level", "top");
        // 2代表持久化
        int deliveryMode = 2;
        // 编码
        String encoding = "UTF-8";
        // ttl，过期时间
        String expiration = "10000";
        // 优先级，默认为5，配合队列的 x-max-priority属性使用
        int priority = 5;
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(deliveryMode)
                .contentEncoding(encoding)
                .expiration(expiration)
                .headers(headers)
                .priority(priority)
                .messageId(String.valueOf(UUID.randomUUID()))
                .build();
        // 声明队列(默认交换机AMQP default, Direct)
        channel.queueDeclare(ORIGIN_QUEUE_NAME, false, false, false, null);
        // 发送消息
        channel.basicPublish("", ORIGIN_QUEUE_NAME, properties, message.getBytes());
        // 关闭channel conn
        channel.close();
        conn.close();
    }

    /**
     * 创建绑定默认交换机的消费者
     * @throws Exception
     */
    public static void defaultConsumeMessage() throws Exception{
        // 创建Connection和Channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 不声明交换机(默认交换机AMQP default, Direct类型)
        channel.queueDeclare(ORIGIN_QUEUE_NAME, false, false, false, null);
        System.err.println("waiting for message");
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message : '" + msg + "' " );
                System.out.println("messageId : " + properties.getMessageId() );
                System.out.println(properties.getHeaders().get("name") + " -- " + properties.getHeaders().get("level"));
            }
        };
        // 开始获取消息
        channel.basicConsume(ORIGIN_QUEUE_NAME, true, consumer);
    }
}
