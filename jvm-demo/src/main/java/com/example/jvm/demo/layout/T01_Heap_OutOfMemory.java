package com.example.jvm.demo.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟OutOfMemoryError
 *
 * @author gaozhilong
 * @date 2020/12/22 9:38
 * @since v1.0.0001
 */
public class T01_Heap_OutOfMemory {

    private static final int _1k = 1024;

    public static void main(String[] args) {
        List<byte[]> byteList = new ArrayList<>();
        quietlyWaitingForCrashHeap(byteList);
    }

    public static void quietlyWaitingForCrashHeap(List<byte[]> byteList) {
        try {
            while (true) {
                    // 每次申请500k的空间
                    byteList.add(new byte[500 * _1k]);
                    Thread.sleep(100);
                }
            }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    模拟参数
    -XX:+UseConcMarkSweepGC
    -XX:+UseCMSInitiatingOccupancyOnly
    -XX:CMSInitiatingOccupancyFraction=70
    -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses
    -XX:+CMSClassUnloadingEnabled
    -XX:+ParallelRefProcEnabled
    -XX:+CMSScavengeBeforeRemark
    -verbose:gc
    -Xms20M
    -Xmx20M
    -Xmn10M
    -XX:+PrintGCDetails
    -XX:SurvivorRatio=8
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:MetaspaceSize=10M
    -XX:MaxMetaspaceSize=10M
    -XX:HeapDumpPath=/Users/fengzheng/jvmlog
    */
}
