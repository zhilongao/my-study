package com.study.mq.kafka.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 19:34
 * @since v1.0.0001
 */
@Component
public class MyKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public String send(@RequestParam String msg) {
        kafkaTemplate.send("spring-boot-topic", msg);
        return "ok";
    }

}
