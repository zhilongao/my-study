package com.example.mq.rabbitmq.dlx;


import com.example.mq.rabbitmq.SelfConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 *  使用延时插件实现的消息投递-生产者
 *  必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 *  先启动消费者
 */
public class DelayPluginProducer implements Runnable{

    public static final String delayExchange = "delay_exchange";

    public static final String delayRoutingKey = "delay_routing_key";

    private Connection conn;

    private Channel channel;

    public DelayPluginProducer() {
        try {
            conn = SelfConnectionFactory.createConnection();
            channel = conn.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 延时投递， 比如延迟10秒(定时投递,可以直接定义delayTime)
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, +10);
        Date delayTime = calendar.getTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "发送时间：" + sf.format(now) + "，投递时间：" + sf.format(delayTime);
        // 延迟时间，目标时刻减去当前时刻
        Map<String, Object> headers = new HashMap<String, Object>(8);
        headers.put("x-delay", delayTime.getTime() - now.getTime());
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
                .headers(headers);
        try {
            channel.basicPublish(delayExchange, delayRoutingKey, props.build(), msg.getBytes());
            channel.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

