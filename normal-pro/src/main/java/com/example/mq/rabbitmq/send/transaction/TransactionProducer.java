package com.example.mq.rabbitmq.send.transaction;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author gaozhilong
 */
public class TransactionProducer implements Runnable{

    private final static String TRANSACTION_EXCHANGE_NAME = "transaction_exchange_name";

    private static final String topicType = "topic";

    private final static String TRANSACTION_QUEUE_NAME = "transaction_queue_name";

    private static final String routingKey = "simpleRoutingKey";

    Connection conn;

    Channel channel;

    public TransactionProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "hello,world rabbitmq";
        try {
            channel.exchangeDeclare(TRANSACTION_EXCHANGE_NAME, topicType);
            channel.queueDeclare(TRANSACTION_QUEUE_NAME, false, false, false, null);
            channel.queueBind(TRANSACTION_QUEUE_NAME, TRANSACTION_EXCHANGE_NAME, routingKey);
            channel.txSelect();
            channel.basicPublish(TRANSACTION_EXCHANGE_NAME, routingKey, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish(TRANSACTION_EXCHANGE_NAME, routingKey, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish(TRANSACTION_EXCHANGE_NAME, routingKey, null, (msg).getBytes());
            channel.txCommit();
            // int i =1/0;
            channel.basicPublish(TRANSACTION_EXCHANGE_NAME, routingKey, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish(TRANSACTION_EXCHANGE_NAME, routingKey, null, (msg).getBytes());
            channel.txCommit();
            // int i =1/0;
            channel.txCommit();
            System.err.println("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.txRollback();
                System.err.println("消息已经回滚!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                channel.close();
                conn.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
