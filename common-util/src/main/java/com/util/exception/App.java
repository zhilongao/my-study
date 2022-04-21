package com.util.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class App {


    public static Map<String, Map<String, Long>> performanceMap = new HashMap<>();

    public static void main(String[] args) {
        App app = new App();
        for (int i = 0; i < 20; i++) {
            app.comparePerformance();
        }
        Set<String> keys = performanceMap.keySet();
        System.err.println("时间          \tm1耗时\tm2耗时");
        for (String key : keys) {
            Map<String, Long> itemMap = performanceMap.get(key);
            Long m1 = itemMap.get("m1");
            Long m2 = itemMap.get("m2");
            System.err.println(key + "\t" + m1 + "\t    " + m2);
        }
    }

    public void comparePerformance() {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            try {
                m1();
            } catch (Exception e) {

            }
        }
        long l1 = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            try {
                m2();
            } catch (Exception e) {

            }
        }
        long cost1 = l1 - s;
        long cost2 = System.currentTimeMillis() - l1;
        Map<String, Long> item = new HashMap<>();
        item.put("m1", cost1);
        item.put("m2", cost2);
        performanceMap.put(String.valueOf(System.currentTimeMillis()), item);
    }


    private void m1() {
        for (int i = 0; i < 10000; i++) {

        }
        BusinessExceptionV1 businessExceptionV1 = new BusinessExceptionV1();
        businessExceptionV1.setReason("异常原因1");
        throw businessExceptionV1;
    }

    private void m2() {
        for (int i = 0; i < 10000; i++) {

        }
        BusinessExceptionV2 businessExceptionV2 = new BusinessExceptionV2();
        businessExceptionV2.setReason("异常原因1");
        throw businessExceptionV2;
    }

}
