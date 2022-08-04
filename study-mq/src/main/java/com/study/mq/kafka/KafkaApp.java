package com.study.mq.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
public class KafkaApp {

    public static void main(String[] args) {
        //SpringApplication.run(KafkaApp.class, args);
        KafkaApp app = new KafkaApp();
        int[] coins=new int[]{1, 3, 5, 6, 7};
        int amount = 30;
        app.coinChange(coins, amount);
    }

    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public int change(int amount, int[] coins) {

        return 0;
    }


}
