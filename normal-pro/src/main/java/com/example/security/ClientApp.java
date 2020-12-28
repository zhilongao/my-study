package com.example.security;


import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/28 15:40
 * @since v1.0.0001
 */
public class ClientApp {

    private static final String algorithmMd5 = "MD5";

    private static List<String> records = new ArrayList<>();

    public static void main(String[] args) {
        String password1 = "1234567";
        String password2 = "abcdefg";
        String password3 = "1111111";
        records.add(encode(password1));
        records.add(encode(password2));
        System.err.println(JSONObject.toJSONString(records));
        System.err.println(verify(password1));
        System.err.println(verify(password2));
        System.err.println(verify(password3));
    }

    /**
     * 将用户输入的明文密码经过md5加密，然后base64转换成字符串存储。
     * @param message
     * @return
     */
    private static String encode(String message) {
        MessageDigest digestInstance = null;
        try {
            digestInstance = MessageDigest.getInstance(algorithmMd5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] inBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] digest = digestInstance.digest(inBytes);
        BASE64Encoder encoder = new BASE64Encoder();
        String res = encoder.encode(digest);
        return res;
    }

    /**
     * 用户输入明文密码，先执行存储时的步骤，然后验证。
     * @param message
     * @return
     */
    private static boolean verify(String message) {
        String encode = encode(message);
        if (records.contains(encode)) {
            return true;
        }
        return false;
    }
}
