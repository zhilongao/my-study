package com.example.mq.rabbitmq.returnlistener;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 9:28
 * @since v1.0.0001
 */
public class ReturnListenerTask implements Runnable {

    public static String alternateExchangeName = "simple_alternate_exchange";

    public static String alternateExchangeType = "topic";

    public static String alternateQueueName = "simple_alternate_queue";

    public static String alternateRoutingKey = "#";

    public static String testExchangeName1 = "simple_test_exchange_1";

    public static String testExchangeType = "topic";

    public static String testExchangeName2 = "simple_test_exchange_2";

    public static String testRoutingKey = "simple_test_routing_key1";

    private Connection conn;

    private Channel channel;

    public ReturnListenerTask() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 给channel添加一个returnListener
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("=========监听器收到了无法路由，被返回的消息============");
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("message:" + new String(body));
            }
        });
        try {
            // 设置基本属性
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).
                    contentEncoding("UTF-8").build();
            // 声明备份交换机和备份队列，并通过路由规则#绑定
            channel.exchangeDeclare(alternateExchangeName, alternateExchangeType, false, false, false, null);
            channel.queueDeclare(alternateQueueName, false, false, false, null);
            channel.queueBind(alternateQueueName, alternateExchangeName, alternateRoutingKey);
            // 声明一个交换机，声明时指定备份交换机属性
            Map<String,Object> arguments = new HashMap<String,Object>(8);
            arguments.put("alternate-exchange", alternateExchangeName);
            channel.exchangeDeclare(testExchangeName1, "topic", false, false, false, arguments);
            // 向该交换机发送消息，当消息无法被路由到某个队列，会将消息转发到备份交换机。
            channel.basicPublish(testExchangeName1, testRoutingKey, true, properties, "11111".getBytes());

            // 第三个参数是设置的mandatory，如果mandatory是false，消息也会被直接丢弃
            // 声明一个交换机，声明时不指定备份交换机属性
            channel.exchangeDeclare(testExchangeName2, testExchangeType, false, false, false, null);
            // 向该交换机发送消息，当消息无法路由到某个队列时，会被退回到channel，由于channel上面绑定了监听函数，该函数处理退回的消息
            channel.basicPublish(testExchangeName2, testRoutingKey,true, properties,"只为更好的你".getBytes());
            TimeUnit.SECONDS.sleep(10);
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
        }
    }
}
