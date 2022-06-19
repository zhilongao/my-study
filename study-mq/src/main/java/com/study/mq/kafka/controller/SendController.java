package com.study.mq.kafka.controller;

import com.study.mq.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class SendController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/kafka/send")
    public String send(@RequestParam("msg") String msg) {
        kafkaProducer.send(msg);
        return "ok";
    }

}
