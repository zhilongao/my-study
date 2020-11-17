package com.study.mq.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消息监听器，监听某个主题的消息，需要提供topics和groupId
 */
@Component
public class ConsumerListener {
    /**
     * 消费主题
     */
    private static final String testTopic = "spring-boot-topic";

    /**
     * 消费组id
     */
    private static final String testGroup = "spring-boot-topic-group";

    @KafkaListener(topics = testTopic, groupId = testGroup)
    public void onMessage(String msg){
        System.err.println("----收到消息：" + msg + "----");
    }
}
