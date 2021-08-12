
package com.example.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/31 9:36
 * @since v1.0.0001
 */

public class Md5Example {

    public static final String algorithmMd5 = "MD5";

    public static final List<String> records = new ArrayList<>();


     /**
     * 将用户输入的明文密码经过md5加密，然后base64转换成字符串存储。
     * @param message
     * @return
     */
    public static String encode(String message, boolean store) {
        MessageDigest digestInstance = null;
        try {
            digestInstance = MessageDigest.getInstance(algorithmMd5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] inBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] digest = digestInstance.digest(inBytes);
        byte[] resBytes = Base64.getEncoder().encode(digest);
        String res = new String(resBytes, StandardCharsets.UTF_8);
        if (store) {
            records.add(res);
        }
        return res;
    }

    /**
     * 注册的逻辑
     * @param message message 明文
     */
    public static void register(String message) {
        encode(message, true);
    }

     /**
     * 验证的逻辑
     * @param message 明文
     * @return 是否验证通过
     */
    public static boolean verify(String message) {
        String encode = encode(message, false);
        return records.contains(encode);
    }

    public static void main(String[] args) {
        String password1 = "1234567";
        String password2 = "abcdefg";
        String password3 = "1111111";
        register(password1);
        register(password3);
        boolean verify1 = verify(password1);
        boolean verify2 = verify(password2);
        boolean verify3 = verify(password3);
        System.err.println(verify1);
        System.err.println(verify2);
        System.err.println(verify3);
    }
}
