package com.study.mq.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: qingshan
 */
@Component
public class KafkaProducer {

    public static final String testTopic = "spring-boot-topic";

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public String send(@RequestParam String msg){
        kafkaTemplate.send(testTopic, msg);
        return "ok";
    }
}
