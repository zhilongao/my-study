package com.study.stu.model;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {
    public static void main(String[] args) throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        new Thread(() -> {
            while (true) {
                try {
                    System.err.println("服务启动");
                    // 1. 阻塞方法,获取新的连接
                    final Socket socket = server.accept();
                    // 2. 每个连接都创建一个新线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.err.println(new String(data, 0, len));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
