package com.util.file.transfer.common;

import java.io.InputStream;

/**
 * @author gaozhilong
 * @date 2021/6/9 16:46
 */
public class StreamTool {

    public static String PROTOCOL_END_FLAG = "over";

    public static String readLine(InputStream is) {
        StringBuilder sb = new StringBuilder();
        byte[] data = new byte[1024];
        int len;
        try {
            while ((len = is.read(data)) != -1) {
                String message = new String(data, 0, len);
                sb.append(message);
                if (message.contains(PROTOCOL_END_FLAG)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().substring(0, sb.toString().length() - PROTOCOL_END_FLAG.length());
    }

}
