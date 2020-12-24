package com.example.mq.rabbitmq.confirm;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class BatchConfirmProducer implements Runnable{

    private final static String QUEUE_NAME = "original_queue_batch";

    Connection conn;

    Channel channel;

    public BatchConfirmProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "Hello world, Rabbit MQ ,Batch Confirm";
        try {
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            try {
                channel.confirmSelect();
                int limit = 5;
                for (int i = 0; i < limit; i++) {
                    // 发送消息(默认交换机,routingKey为队列名称)
                    channel.basicPublish("", QUEUE_NAME, null, (msg +"-"+ i).getBytes());
                }
                // 批量确认结果，ACK如果是Multiple=True，代表ACK里面的Delivery-Tag之前的消息都被确认了
                // 比如5条消息可能只收到1个ACK，也可能收到2个（抓包才看得到）
                // 直到所有信息都发布，只要有一个未被Broker确认就会IOException
                channel.waitForConfirmsOrDie();
                System.out.println("消息发送完毕，批量确认成功");
            } catch (Exception e) {
                // 发生异常，可能需要对所有消息进行重发
                e.printStackTrace();
            }
            channel.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

