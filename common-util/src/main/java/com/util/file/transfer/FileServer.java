package com.util.file.transfer;

import com.util.file.transfer.server.ServerInstance;

/**
 * @author gaozhilong
 * @date 2021/6/9 16:40
 */
public class FileServer {
    public static void main(String[] args) {
        ServerInstance instance = new ServerInstance(9091);
        try {
            instance.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
