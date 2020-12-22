package com.example.mq.rabbitmq.confirm;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class BatchConfirmProducer {
    private final static String QUEUE_NAME = "original_queue_batch";

    public static void main(String[] args) throws Exception {
        // 建立连接
        Connection conn = SelfConnectionFactory.createConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();
        String msg = "Hello world, Rabbit MQ ,Batch Confirm";
        // 声明队列（默认交换机AMQP default，Direct）
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        try {
            channel.confirmSelect();
            for (int i = 0; i < 5; i++) {
                // 发送消息
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
    }
}

