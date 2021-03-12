package com.example.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gaozhilong
 */
public class Consumer {

    public static final Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();

    public static final String DEFAULT_CONSUMER_GROUP_NAME = "my_test_consumer_group";

    public static final String DEFAULT_NAME_SERVER_ADDR = "192.168.8.128:9876";

    public static final String DEFAULT_TOPIC_NAME = "test_topic_1";

    public static void main(String[] args) throws MQClientException {
        // 创建消费者
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(DEFAULT_CONSUMER_GROUP_NAME);
        consumer.setNamesrvAddr(DEFAULT_NAME_SERVER_ADDR);
        consumer.start();
        // 从指定topic中拉取所有消息队列
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(DEFAULT_TOPIC_NAME);
        for (MessageQueue mq : mqs) {
            System.err.println("Consume from the queue: " + mq);
            SINGLE_MQ:
            while (true) {
                try {
                    // 获取消息的offset，指定从store中获取
                    long offset = consumer.fetchConsumeOffset(mq, true);
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    if (null != pullResult.getMsgFoundList()) {
                        for (MessageExt messageExt : pullResult.getMsgFoundList()) {
                            System.err.print(new String(messageExt.getBody()));
                            System.err.print(pullResult);
                            System.err.println(messageExt);
                        }
                    }
                    offsetTable.put(mq, pullResult.getNextBeginOffset());
                    PullStatus pullStatus = pullResult.getPullStatus();
                    switch (pullStatus) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        consumer.shutdown();
    }


    // 需要消费者自己记录消费的偏移量  因为消息在broker上是持久的 (
    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null){
            return offset;
        }
        return 0;
    }

}
