package com.example.redis.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:50
 * @since v1.0.0001
 */
public class SelfClient {

    private Socket socket;

    private OutputStream write;

    private InputStream read;

    public SelfClient(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        write = socket.getOutputStream();
        read = socket.getInputStream();
    }

    public void set(String key, String value) throws IOException {
        StringBuilder sb = new StringBuilder();
        // 代表三个参数
        sb.append("*3").append("\r\n");

        // 第一个参数(get)的长度
        sb.append("$3").append("\r\n");
        // 第一个参数的内容
        sb.append("SET").append("\r\n");

        // 第二个参数key的长度
        sb.append("$").append(key.getBytes().length).append("\r\n");
        // 第二个参数key的内容
        sb.append(key).append("\r\n");

        // 第三个参数value的长度
        sb.append("$").append(value.getBytes().length).append("\r\n");
        // 第三个参数value的内容
        sb.append(value).append("\r\n");

        // 发送报文内容
        write.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        read.read(bytes);
        System.out.println("-------------set-------------");
        System.out.println(new String(bytes));
    }

    public void get(String key) throws IOException {
        StringBuffer sb = new StringBuffer();
        // 代表2个参数
        sb.append("*2").append("\r\n");
        // 第一个参数(get)的长度
        sb.append("$3").append("\r\n");
        // 第一个参数的内容
        sb.append("GET").append("\r\n");

        // 第二个参数长度
        sb.append("$").append(key.getBytes().length).append("\r\n");
        // 第二个参数内容
        sb.append(key).append("\r\n");

        write.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        read.read(bytes);
        System.out.println("-------------get-------------");
        System.out.println(new String(bytes));
    }

    public static void main(String[] args) throws IOException{
        String ip = "192.168.8.128";
        int port = 6379;
        SelfClient client = new SelfClient(ip, port);
        client.set("qingshan", "2673");
        client.get("qingshan");
    }
}
