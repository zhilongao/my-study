package com.util.file.transfer.client;

import com.util.file.transfer.common.ConfigUtils;
import com.util.file.transfer.common.LogsUtil;
import com.util.file.transfer.common.StreamTool;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    String url;

    public ClientHandler( String url) {
        this.url = url;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            String host = ConfigUtils.get("file.server.host");
            int port = Integer.parseInt(ConfigUtils.get("file.server.port"));
            socket = new Socket(host, port);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            // 1.首先发送协议数据
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);
            int available = fis.available();
            long sourceId = System.currentTimeMillis();
            String protocolSuffix = ConfigUtils.get("protocol.suffix");
            String head = "Content-Length=" + available
                    + ";filename=" + file.getName() + ";sourceid=" + sourceId + protocolSuffix;
            os.write(head.getBytes(StandardCharsets.UTF_8));
            os.flush();
            // 2.读取服务端的响应
            String resp = StreamTool.readLine(is);
            LogsUtil.info("receive resp: " + resp);
            String[] items = resp.split(";");
            String responseId = items[0].substring(items[0].indexOf("=") + 1);
            LogsUtil.info("responseId=" + responseId);
            // 3.向服务端写数据
            byte[] writeByte = new byte[available];
            fis.read(writeByte);
            socket.getOutputStream().write(writeByte);
            socket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
