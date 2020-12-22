package com.example.mq.rabbitmq.rpc;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * SimpleRpcServer
 * @author gaozhilong
 */
public class SimpleRpcServer {

    private final static String REQUEST_QUEUE_NAME = "rpc_request_queue";

    public final static String RPC_EXCHANGE_NAME = "rpc_exchange";

    public static final String EXCHANGE_TYPE_DIRECT = "direct";

    public final static String RPC_ROUTING_KEY = "rpc_message_key_22";

    public static void handleMessage() throws Exception {
        // 1. 创建connection和channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(RPC_EXCHANGE_NAME, EXCHANGE_TYPE_DIRECT, false, false, null);
        // 2. 声明队列(请求队列)
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        // 3. 设置prefetch的值，一次处理一条数据
        channel.basicQos(1);
        channel.queueBind(REQUEST_QUEUE_NAME, RPC_EXCHANGE_NAME, RPC_ROUTING_KEY);
        // 4. 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // 获取客户端指定的回调队列名
                String replayQueue = properties.getReplyTo();
                try {
                    // 处理结果
                    handleMessage(properties,replayQueue, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(REQUEST_QUEUE_NAME, true, consumer);
    }

    public static void handleMessage(BasicProperties properties, String replayQueue, byte[] body) throws Exception{
        String message = new String(body, StandardCharsets.UTF_8);
        System.err.printf("queueName %s, message %s", replayQueue, message);
        System.err.println();
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        channel.basicPublish(RPC_EXCHANGE_NAME, RPC_ROUTING_KEY, null, message.getBytes());
        channel.close();
        conn.close();
    }
}
