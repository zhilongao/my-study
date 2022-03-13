package com.util.redis.jedis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleProtocol {

    public String execute(String cmd, String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        // 向redis服务器写
        os.write(cmd.getBytes());
        //从redis服务器读,到bytes中
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        return new String(bytes, 0, len);
    }

}
