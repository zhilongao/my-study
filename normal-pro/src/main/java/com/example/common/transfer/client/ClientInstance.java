package com.example.common.transfer.client;

/**
 * @author gaozhilong
 * @date 2021/6/11 14:12
 */
public class ClientInstance {

    public void start() {
        new Thread(new ClientHandler("D:\\temp\\message.txt")).start();
    }
}
