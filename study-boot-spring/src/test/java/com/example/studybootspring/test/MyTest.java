package com.example.studybootspring.test;

import com.study.mq.kafka.component.MyKafkaProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 19:36
 * @since v1.0.0001
 */
@SpringBootTest
public class MyTest {
    @Autowired
    MyKafkaProducer myKafkaProducer;

    @Test
    void contextLoads() {
        long currentTimeMillis = System.currentTimeMillis();
        System.err.println(currentTimeMillis);
        myKafkaProducer.send("gzl发送消息");
    }
}
