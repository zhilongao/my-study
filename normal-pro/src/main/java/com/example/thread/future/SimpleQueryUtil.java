package com.example.thread.future;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/25 13:47
 * @since v1.0.0001
 */
public class SimpleQueryUtil {

    public static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        return "601857";
    }

    public static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    public static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double fixNum = 0.3;
        if (Math.random() < fixNum) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }

    public static Double fetchPrice(String code, String url) {
        System.err.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5 + Math.random() * 20;
    }
}
