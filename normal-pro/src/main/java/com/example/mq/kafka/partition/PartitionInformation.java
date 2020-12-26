package com.example.mq.kafka.partition;

import com.example.mq.kafka.CommonConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Properties;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/16 17:29
 * @since v1.0.0001
 */
public class PartitionInformation {

    public static void printTopicInfo(String topic) {
        Properties props = createProp();
        Producer<String,String> producer = new KafkaProducer<String,String>(props);
        List<PartitionInfo> list = producer.partitionsFor(topic);
        for(PartitionInfo part : list){
            System.err.println(part.partition() + "   " + part.leader());
        }
    }

    private static Properties createProp() {
        Properties props=new Properties();
        props.put("bootstrap.servers", CommonConstant.bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 0 发出去就确认 | 1 leader 落盘就确认| all(-1) 所有Follower同步完才确认
        props.put("acks","1");
        // 异常自动重试次数
        props.put("retries",3);
        // 多少条数据发送一次，默认16K
        props.put("batch.size",16384);
        // 批量发送的等待时间
        props.put("linger.ms",5);
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送
        props.put("buffer.memory",33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        props.put("max.block.ms",3000);
        return props;
    }
}
