package com.example.mq.rabbitmq.returnlistener;

import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 * 当消息无法匹配到队列时，会发回给生产者
 */
public class ReturnListenerProducer {

    public static String alternateExchangeName = "simple_alternate_exchange";

    public static String alternateExchangeType = "topic";

    public static String alternateQueueName = "simple_alternate_queue";

    public static String alternateRoutingKey = "#";

    public static String testExchangeName1 = "simple_test_exchange_1";

    public static String testExchangeType = "topic";

    public static String testExchangeName2 = "simple_test_exchange_2";

    public static String testRoutingKey = "simple_test_routing_key1";

    public static void main(String[] args) throws Exception{
        Connection connection = SelfConnectionFactory.createConnection();
        Channel channel = connection.createChannel();
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
        /************************ 备份交换机测试***********************/
        // 设置基本属性
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).
                contentEncoding("UTF-8").build();
        // 声明一个备份交换机
        channel.exchangeDeclare(alternateExchangeName, alternateExchangeType, false, false, false, null);
        // 声明一个队列
        channel.queueDeclare(alternateQueueName, false, false, false, null);
        // channel绑定备份交换机 备份队列 routingKey
        channel.queueBind(alternateQueueName, alternateExchangeName, alternateRoutingKey);
        // 在声明交换机的时候指定备份交换机
        Map<String,Object> arguments = new HashMap<String,Object>(8);
        arguments.put("alternate-exchange", alternateExchangeName);
        channel.exchangeDeclare(testExchangeName1, "topic", false, false, false, arguments);
        channel.basicPublish(testExchangeName1, testRoutingKey, true, properties, "11111".getBytes());

        /**************************** 消息发送回来测试 **********************/
        // 发送到了默认的交换机上，由于没有任何队列使用这个关键字跟交换机绑定，所以会被退回
        // 第三个参数是设置的mandatory，如果mandatory是false，消息也会被直接丢弃
        channel.exchangeDeclare(testExchangeName2, testExchangeType, false, false, false, null);
        channel.basicPublish(testExchangeName2, testRoutingKey,true, properties,"只为更好的你".getBytes());
        TimeUnit.SECONDS.sleep(10);
        channel.close();
        connection.close();
    }
}
