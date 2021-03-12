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

    public static final int MESSAGE_LIMIT = 6;

    public static final String messageTags = "tagA";

    public static final String messageKeys = "2673";

    public static void main(String[] args) throws MQClientException {
        produceMessage();
    }

    public static void produceMessage() throws MQClientException{
        DefaultMQProducer producer = new DefaultMQProducer(RocketCommonConstant.PRODUCER_GROUP_SIMPLE_NAME);
        producer.setNamesrvAddr(RocketCommonConstant.NAME_SERVER_ADDR);
        producer.setSendMsgTimeout(RocketCommonConstant.SEND_MSG_TIMEOUT);
        producer.start();
        for (int i = 0; i < MESSAGE_LIMIT; i++){
            try {
                // tags用于过滤消息
                // keys索引键,多个用空格隔开,RocketMQ可以根据这些key快速检索到消息
                String message = "RocketMQ" + String.format("%05d", i);
                Message msg = new Message(RocketCommonConstant.MESSAGE_TOPIC_NAME_1, messageTags, messageKeys, message.getBytes());
                SendResult sendResult = producer.send(msg);
                System.err.println(String.format("%05d", i) + " : " + sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
