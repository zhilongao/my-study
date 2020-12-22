package com.example.mq.rabbitmq.rpc;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/22 13:55
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ListenerClient.listenerResMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleRpcServer.handleMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            Random random = new Random();
            @Override
            public void run() {
                for(;;) {
                    String num = 5 + "";
                    try {
                        SimpleRpcClient.sendMessage(num);
                        TimeUnit.MILLISECONDS.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
