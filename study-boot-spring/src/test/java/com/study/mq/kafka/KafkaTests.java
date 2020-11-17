package com.study.mq.kafka;

import com.study.mq.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qingshan
 */
@SpringBootTest
class KafkaTests {

    @Autowired
    KafkaProducer producer;

    // 消费者：先启动 kafkaApp
    @Test
    void testSendMsg() {
        long time = System.currentTimeMillis();
        System.err.println("----" + time + "，已经发出----");
        producer.send("this is a test message , "  + time);
        System.err.println("----" + time + "，消息发送完毕----");
    }
}
