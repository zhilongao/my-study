package com.example.mq.kafka.assign.singlepart;


import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: qingshan
 */
public class SinglePartitionProducer {

    public static final String topicName = "simple-test-topic-22";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonProducerProps();
        Producer<String,String> producer = new KafkaProducer<String, String>(props);
        // 全部发送到一个分区，不知道哪位幸运儿能消费到
        // 分区在 /tmp/kafka-logs2 下
        producer.send(new ProducerRecord<String,String>(topicName,0,"0","0qs"));
        producer.send(new ProducerRecord<String,String>(topicName,1,"1","1qs"));
        producer.send(new ProducerRecord<String,String>(topicName,2,"2","2qs"));
        producer.send(new ProducerRecord<String,String>(topicName,2,"3","3qs"));
        producer.send(new ProducerRecord<String,String>(topicName,2,"4","4qs"));
        producer.close();
    }

}
