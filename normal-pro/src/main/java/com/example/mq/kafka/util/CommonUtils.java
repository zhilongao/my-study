package com.example.mq.kafka.util;

import com.example.mq.kafka.CommonConstant;

import java.util.Properties;

/**
 * 工具类，封装初始化producer和consumer的properties
 * @author gaozhilong
 * @date 2021/3/10 13:40
 * @since v1.0.0001
 */
public class CommonUtils {

    public static Properties initCommonProps() {
        Properties props = new Properties();
        // kafka连接地址
        props.put("bootstrap.servers", CommonConstant.bootstrapServers);
        return props;
    }

    public static Properties initProducerProps() {
        Properties props = initCommonProps();
        // key和value的序列化方式
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        // 确认机制 0:发送出去就确认 1:leader落盘就确认  all(-1) 所有follower同步完才确认
        props.put("acks","1");
        // 异常自动重试次数
        props.put("retries",  3);
        // 多少条数据发送一次，默认16k
        props.put("batch.size",16384);
        // 批量发送的等待时间
        props.put("linger.ms",5);
        // 客户端缓冲区大小，默认32M 满了也会触发消息发送
        props.put("buffer.memory",33554432);
        // 获取元数据时生产者的阻塞时间 超时后抛出异常
        props.put("max.block.ms",3000);
        return props;
    }

    public static Properties initConsumerProps() {
        Properties props = initCommonProps();
        // 消费端groupId
        props.put("group.id", CommonConstant.SIMPLE_TEST_GROUP);
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit","true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms","1000");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset","earliest");
        // key和value的反序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
