package com.example.mq.rabbitmq.transaction;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TransactionProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE1";

    public static void main(String[] args) throws Exception {
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();

        String msg = "hello,world rabbitmq";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            int i =1/0;
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            // int i =1/0;
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (Exception e) {
            channel.txRollback();
            System.err.println("消息已经回滚!");
        }
        channel.close();
        conn.close();
    }

}
