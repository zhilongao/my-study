package com.example.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/11 17:29
 * @since v1.0.0001
 */
public class CommonUtil {

    /**
     * 删除交换机
     * @param exchangeName exchangeName
     * @throws Exception
     */
    public static void deleteExchange(String exchangeName) throws Exception{
        // 获取链接，创建channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 删除交换机
        channel.exchangeDelete(exchangeName);
        // 关闭channel 关闭conn
        channel.close();
        conn.close();
    }

    /**
     * 删除队列
     * @param queueName queueName
     * @throws Exception
     */
    public static void deleteQueue(String queueName) throws Exception {
        // 获取链接，创建channel
        Connection conn = SelfConnectionFactory.createConnection();
        Channel channel = conn.createChannel();
        // 删除队列
        channel.queueDelete(queueName);
        // 关闭channel 关闭conn
        channel.close();
        conn.close();
    }

}
