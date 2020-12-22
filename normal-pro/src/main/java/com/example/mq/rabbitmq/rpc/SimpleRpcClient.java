package com.example.mq.rabbitmq.rpc;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/14 15:14
 * @since v1.0.0001
 */
public class SimpleRpcClient {

    private final static String REQUEST_QUEUE_NAME = "rpc_request_queue";

    private final static String RESPONSE_QUEUE_NAME = "rpc_response_queue";

    public final static String RPC_EXCHANGE_NAME = "rpc_exchange";

    public static final String EXCHANGE_TYPE_DIRECT = "direct";

    public final static String RPC_ROUTING_KEY = "rpc_message_key";

    public SimpleRpcClient() {

    }

    public static void sendMessage(String message) throws  Exception {
        // 创建一个新的连接 即TCP连接
        Connection connection = SelfConnectionFactory.createConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RPC_EXCHANGE_NAME, EXCHANGE_TYPE_DIRECT, false, false, null);
        // 声明队列(请求队列和响应队列)
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        // 定义消息属性中的correlationId
        String correlationId = java.util.UUID.randomUUID().toString();
        // 设置消息属性的replyTo和correlationId
        BasicProperties properties = new BasicProperties.Builder()
                .correlationId(correlationId)
                .replyTo(RESPONSE_QUEUE_NAME)
                .build();
        // 发送消息到请求队列rpc_request队列
        // 消息发送到与routingKey参数相同的队列中
        channel.basicPublish(RPC_EXCHANGE_NAME, RPC_ROUTING_KEY, properties, message.getBytes());
        // channel.basicPublish("", REQUEST_QUEUE_NAME, properties, message.getBytes());
    }
}
