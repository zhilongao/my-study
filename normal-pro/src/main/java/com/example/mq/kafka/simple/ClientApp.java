package com.example.mq.kafka.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 16:20
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) {
        // startClient();
        test1();
    }

    private static void startClient() {
        SimpleProducer simpleProducer = new SimpleProducer();
        SimpleConsumer simpleConsumer = new SimpleConsumer();
        Thread t1 = new Thread(simpleProducer);
        Thread t2 = new Thread(simpleConsumer);
        t1.start();
        t2.start();
    }

    private static void test1() {
        String message1 = "hello1111";
        String message2 = "world22222";
        System.err.println(message1.substring(3, 3));
        System.err.println(message1.substring(2, 3));
        System.err.println(message1.substring(1, 3));
        System.err.println(message1.substring(0, 3));
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<String>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length() ; i++) {
            for (int j = 0; j <= i; j ++) {
                if (!dp[i-j]) {
                    continue;
                }
                if (dict.contains(s.substring(i-j,i))) {
                    dp[i] = true;
                }
            }
        }
        return dp[s.length()];
    }

}
