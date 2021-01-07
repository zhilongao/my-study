package com.example.mq.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/5 19:25
 * @since v1.0.0001
 */
public class SimpleProducer {

    private static String producerGroup = "simpleGroup";

    private static String namesrvAddr = "192.168.8.128:9876";

    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("my_test_producer_group");
        producer.setNamesrvAddr("192.168.8.128:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();


        for (int i = 0; i < 6; i++){
            try {
                // tags 用于过滤消息 keys 索引键,多个用空格隔开,RocketMQ可以根据这些key快速检索到消息
                Message msg = new Message("q-2-1",
                        "TagA",
                        "2673",
                        ("RocketMQ "+String.format("%05d", i)).getBytes());

                SendResult sendResult = producer.send(msg);
                System.out.println(String.format("%05d", i)+" : "+sendResult);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }

}
