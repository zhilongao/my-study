package com.example.mq.rabbitmq.rpc;

import com.example.mq.rabbitmq.common.ConnectionFactory;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RPCServer {
    public static void main(String[] args) throws Exception{
        // 1.创建connection和channel
        Connection conn = ConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 2.声明队列
        String queueName = "rpcQueue";
        channel.queueDeclare(queueName, true, false, false, null);
        // 设置prefetch的值，一次处理一条数据
        channel.basicQos(1);
        // 3.创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                BasicProperties replyProperties = new BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                // 获取客户端指定的回调队列名
                String replayQueue = properties.getReplyTo();
                // 返回获取消息的平方
                String message = new String(body, StandardCharsets.UTF_8);
                // 计算平方
                Double mSquare =  Math.pow(Integer.parseInt(message),2);
                String repMsg = String.valueOf(mSquare);
                // 把结果发送到回复队列
                channel.basicPublish("", replayQueue, replyProperties, repMsg.getBytes());
                // 手动回应消息应答
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
