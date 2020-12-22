package com.example.mq.rabbitmq.rpc;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/22 14:49
 * @since v1.0.0001
 */
public class ListenerClient {

    private final static String RESPONSE_QUEUE_NAME = "rpc_response_queue";

    public final static String RPC_EXCHANGE_NAME = "rpc_exchange";

    public static final String EXCHANGE_TYPE_DIRECT = "direct";

    public final static String RPC_ROUTING_KEY = "rpc_message_key_22";

    public static void listenerResMessage() throws Exception {
        //创建一个新的连接 即TCP连接
        Connection connection = SelfConnectionFactory.createConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RPC_EXCHANGE_NAME, EXCHANGE_TYPE_DIRECT, false, false, null);
        channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);
        // channel需要绑定队列
        channel.queueBind(RESPONSE_QUEUE_NAME, RPC_EXCHANGE_NAME, RPC_ROUTING_KEY);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.err.printf("监听rpc响应的结果 %s", message);
                System.err.println();
            }
        };
        try {
            channel.basicConsume(RESPONSE_QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
