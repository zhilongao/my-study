package com.example.mq.rabbitmq.returnlistener;

/**
 * @Author: qingshan
 * @Description: 咕泡学院，只为更好的你
 * 当消息无法匹配到队列时，会发回给生产者
 */
public class ClientApp {

    public static void main(String[] args) throws Exception{
        ReturnListenerTask task = new ReturnListenerTask();
        Thread t = new Thread(task);
        t.start();
    }
}
