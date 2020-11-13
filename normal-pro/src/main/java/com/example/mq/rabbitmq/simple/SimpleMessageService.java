package com.example.mq.rabbitmq.simple;

import com.example.mq.rabbitmq.common.ConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleMessageService {

    /**
     * 原生java api生产rabbit mq消息
     * @throws Exception
     */
    public static void originalProduceMessage() throws Exception{
        Connection connection = com.example.mq.rabbitmq.common.ConnectionFactory.createConnection();
        // 通过Connection创建消息通道
        Channel channel = connection.createChannel();
        // 通过channel发送消息 需要指定exchange routingKey BasicProperties byte[] body
        String exchangeName = "simple_exchange";
        String routingKey = "gupao.test";
        String simpleMsg = "hello,rabbit mq";
        channel.basicPublish(exchangeName, routingKey, null, simpleMsg.getBytes());
        // 关闭channel和connection
        channel.close();
        connection.close();
    }

    /**
     * 原生java api消费 rabbit mq 消息
     * @throws Exception
     */
    public static void startSimpleConsumer() throws Exception{
        Connection connection = com.example.mq.rabbitmq.common.ConnectionFactory.createConnection();
        Channel channel = connection.createChannel();
        // 声明交换机
        String exchangeName = "simple_exchange";
        String exchangeType = "direct";
        boolean durable = false;
        boolean autoDelete = false;
        channel.exchangeDeclare(exchangeName, exchangeType, durable, autoDelete, null);
        // 声明队列
        String queueName = "simpleQueue";
        channel.queueDeclare(queueName, false, false, false, null);
        // 绑定队列和交换机
        String routingKey = "gupao.test";
        channel.queueBind(queueName, exchangeName, routingKey);
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
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
    public static void createDefaultExchangeProducer() throws Exception{
        // 创建Connection和Channel
        Connection conn = com.example.mq.rabbitmq.common.ConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 创建headers
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("name", "gupao");
        headers.put("level", "top");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)// 2代表持久化
                .contentEncoding("UTF-8")// 编码
                .expiration("10000")// ttl，过期时间
                .headers(headers)// 自定义属性
                .priority(5)// 优先级，默认为5，配合队列的 x-max-priority属性使用
                .messageId(String.valueOf(UUID.randomUUID()))
                .build();
        String msg = "hello world, rabbit mq";
        // 声明队列(默认交换机AMQP default, Direct)
        // string queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        String queueName = "originalQueue";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("", queueName, properties, msg.getBytes());
        // 关闭channel conn
        channel.close();
        conn.close();
    }

    /**
     * 创建绑定默认交换机的消费者
     * @throws Exception
     */
    public static void createDefaultExchangeConsumer() throws Exception{
        // 创建Connection和Channel
        Connection conn = com.example.mq.rabbitmq.common.ConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 不声明交换机(默认交换机AMQP default, Direct类型)
        // 声明队列
        String queueName = "originalQueue";
        channel.queueDeclare(queueName, false, false, false, null);
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
        channel.basicConsume(queueName, true, consumer);
    }


    /**
     * 删除queue exchange
     * @throws Exception
     */
    public static void deleteQueueAndExchange() throws Exception{
        // 获取链接，创建channel
        Connection conn = ConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 删除queueName和channel
        String[] queueNames = {"ORIGIN_QUEUE","GP_FIRST_QUEUE", "GP_FOURTH_QUEUE", "GP_SECOND_QUEUE", "GP_THIRD_QUEUE",
                "MY_FIRST_QUEUE", "MY_FOURTH_QUEUE", "MY_SECOND_QUEUE", "MY_THIRD_QUEUE",
                "SIMPLE_QUEUE","TEST_TTL_QUEUE","TEST_DLX_QUEUE","DLX_QUEUE",
                "GP_ORI_USE_QUEUE","GP_DEAD_LETTER_QUEUE","DELAY_QUEUE","TEST_LIMIT_QUEUE"};

        String[] exchangeNames = {"GP_DIRECT_EXCHANGE","GP_FANOUT_EXCHANGE", "GP_TOPIC_EXCHANGE",
                "MY_DIRECT_EXCHANGE", "MY_FANOUT_EXCHANGE", "MY_TOPIC_EXCHANGE",
                "SIMPLE_EXCHANGE","DLX_EXCHANGE","GP_ORI_USE_EXCHANGE","GP_DEAD_LETTER_EXCHANGE",
                "DELAY_EXCHANGE","SIMPLE_EXCHANGE"};
        for(String queueName : queueNames){
            channel.queueDelete(queueName);
        }

        for(String exchangeName : exchangeNames){
            channel.exchangeDelete(exchangeName);
        }
        // 关闭channel 关闭conn
        channel.close();
        conn.close();
    }

}
