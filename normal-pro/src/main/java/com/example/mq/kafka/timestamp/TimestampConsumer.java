package com.example.mq.kafka.timestamp;

import com.example.mq.kafka.util.InitPropsUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;


import java.time.Duration;
import java.util.*;


/**
 * TimestampConsumer
 * @author gaozhilong
 */
public class TimestampConsumer {

    private static final String GROUP_ID = "gp-time-group";

    private static final String SIMPLE_TOPIC = "test-timestamp-topic";

    public static void main(String[] args) {
        Properties props = InitPropsUtil.commonConsumerProps(GROUP_ID);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        try {
            // 1. 获取topic的partition信息
            List<PartitionInfo> partitionInfos = consumer.partitionsFor(SIMPLE_TOPIC);
            List<TopicPartition> topicPartitions = new ArrayList<>();
            Map<TopicPartition, Long> timestampsToSearch = new HashMap<>(8);
            Date now = new Date();
            long nowTime = now.getTime();
            // 计算7天之前的时间戳，如果7天前没消息，是查不到的
            long fetchDataTime = nowTime - 1000 * 60 * 60 * 24 * 7;
            System.out.println("时间戳："+fetchDataTime);
            for(PartitionInfo partitionInfo : partitionInfos) {
                topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
                timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), fetchDataTime);
            }
            consumer.assign(topicPartitions);
            // 获取每个partition 7天之前的偏移量
            Map<TopicPartition, OffsetAndTimestamp> map = consumer.offsetsForTimes(timestampsToSearch);
            OffsetAndTimestamp offsetTimestamp = null;
            System.out.println("设置各分区初始偏移量...");
            for(Map.Entry<TopicPartition, OffsetAndTimestamp> entry : map.entrySet()) {
                // 如果设置的查询偏移量的时间点大于最大的索引记录时间，那么value就为空
                offsetTimestamp = entry.getValue();
                if(offsetTimestamp != null) {
                    int partition = entry.getKey().partition();
                    long timestamp = offsetTimestamp.timestamp();
                    long offset = offsetTimestamp.offset();
                    System.out.println("partition = " + partition +
                            ", timestamp = " + timestamp +
                            ", offset = " + offset);
                    // 设置读取消息的偏移量
                    consumer.seek(entry.getKey(), offset);
                }
            }
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("partition = " + record.partition() + ", offset = " + record.offset()+ ", value = " + record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
