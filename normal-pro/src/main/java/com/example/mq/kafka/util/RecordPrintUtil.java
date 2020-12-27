package com.example.mq.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class RecordPrintUtil {

    public static void printRecordMessage(String consumerName, ConsumerRecords<String,String> records) {
        for (ConsumerRecord<String,String> record : records){
            printRecordMessage(consumerName, record);
        }
    }

    public static void printRecordMessage(String consumerName, ConsumerRecord<String,String> record) {
        System.err.printf("----consume = %s----offset = %d ,key = %s, value= %s, partition= %s%n" ,
                consumerName, record.offset(), record.key(), record.value(), record.partition());
    }
}
