package com.example.mq.rabbitmq.dlx;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author gaozhilong
 */
public class DlxProducerTask implements Runnable{

    public static final String produceMessageExchange = "gp_ori_use_exchange";

    public static final String topicExchangeType = "topic";

    public static final String produceMessageQueue = "gp_ori_use_queue";

    public static final String simpleRoutingKey = "simpleRoutingKey";

    public static final String deadLetterExchange = "gp_dead_letter_exchange";

    public Connection conn;

    public Channel channel;

    public DlxProducerTask() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            // 声明exchange和queue
            channel.exchangeDeclare(produceMessageExchange, topicExchangeType);
            // 指定队列的死信交换机
            Map<String, Object> paramsMap = new HashMap<String, Object>(8);
            paramsMap.put("x-dead-letter-exchange", deadLetterExchange);
            // 声明原始队列，参数指定绑定的死信交换机
            channel.queueDeclare(produceMessageQueue, false, false, false, paramsMap);
            channel.queueBind(produceMessageQueue, produceMessageExchange, simpleRoutingKey);
            String basicMessage = "Hello world, Rabbit MQ, DLX MSG";
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    // 持久化消息
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    // TTL(10秒钟过期)
                    .expiration("10000")
                    .build();
            int suffix = 0;
            for (;;) {
                String message = basicMessage + " " + suffix++;
                try {
                    channel.basicPublish(produceMessageExchange, simpleRoutingKey, properties, message.getBytes());
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        channel.close();
                        conn.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (TimeoutException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
