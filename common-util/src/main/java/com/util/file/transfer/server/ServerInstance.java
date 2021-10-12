package com.util.file.transfer.server;

import com.util.file.transfer.common.LogsUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gaozhilong
 * @date 2021/6/11 13:57
 */
public class ServerInstance {

    private ExecutorService executorService;

    private int port;

    private boolean quit = false;

    private ServerSocket server;

    public ServerInstance(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);
    }

    public void quit() {
        this.quit = true;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        server = new ServerSocket(port);
        while(!quit) {
            try {
                LogsUtil.info("file server start!");
                Socket socket = server.accept();
                executorService.execute(new ServerHandler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
