package com.charles.trace.agent.client;

import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("hello,world " + i);
        }
    }
}
