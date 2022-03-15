package com.util.redis.jedis;

import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Data
public class SimpleProtocol {

    private String host;

    private int port;

    public SimpleProtocol(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public String execute(String cmd) throws Exception {
        Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        // 向redis服务器写
        os.write(cmd.getBytes());
        //从redis服务器读,到bytes中
        byte[] bytes = new byte[2048];
        int len = is.read(bytes);
        return new String(bytes, 0, len);
    }

}
