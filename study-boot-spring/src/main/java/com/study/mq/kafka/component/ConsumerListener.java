package com.study.mq.kafka.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 19:31
 * @since v1.0.0001
 */
@Component
public class ConsumerListener {

    @KafkaListener(topics = "spring-boot-topic", groupId = "spring-boot-group")
    public void onMessage(String msg) {
        System.err.println("接收到消息-->" + msg);
    }
}
