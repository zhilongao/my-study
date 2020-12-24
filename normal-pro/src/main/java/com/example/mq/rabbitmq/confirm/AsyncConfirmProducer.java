package com.example.mq.rabbitmq.confirm;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author gaozhilong
 */
public class AsyncConfirmProducer implements Runnable{

    private final static String QUEUE_NAME = "original_queue_async";

    Connection conn;

    Channel channel;

    public AsyncConfirmProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "Hello world, Rabbit MQ, Async Confirm";
        try {
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 声明一个SortedSet，用来维护未确认消息的deliveryTag
            final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
            // 这里不会打印所有响应的ACK； ACK可能有多个，有可能一次确认多条，也有可能一次确认一条
            // 异步监听确认和未确认的消息
            // 如果要重复运行，先停掉之前的生产者，清空队列
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("Broker未确认消息，标识：" + deliveryTag);
                    if (multiple) {
                        // headSet表示后面参数之前的所有元素，全部删除
                        confirmSet.headSet(deliveryTag + 1L).clear();
                    } else {
                        confirmSet.remove(deliveryTag);
                    }
                    // 这里添加重发的方法
                }
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    // 如果true表示批量执行了deliveryTag这个值以前（小于deliveryTag的）的所有消息，如果为false的话表示单条确认
                    System.out.println(String.format("Broker已确认消息，标识：%d，多个消息：%b", deliveryTag, multiple));
                    if (multiple) {
                        // headSet表示后面参数之前的所有元素，全部删除
                        confirmSet.headSet(deliveryTag + 1L).clear();
                    } else {
                        // 只移除一个元素
                        confirmSet.remove(deliveryTag);
                    }
                    System.out.println("未确认的消息:" + confirmSet);
                }
            });
            // 开启发送方确认模式
            channel.confirmSelect();
            for (int i = 0; i < 10; i++) {
                long nextSeqNo = channel.getNextPublishSeqNo();
                // 发送消息
                channel.basicPublish("", QUEUE_NAME, null, (msg +"-"+ i).getBytes());
                confirmSet.add(nextSeqNo);
            }
            System.out.println("所有消息:"+confirmSet);
            // 这里注释掉的原因是如果先关闭了，可能收不到后面的ACK
            //channel.close();
            //conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

