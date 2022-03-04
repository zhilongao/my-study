package com.util.security;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        SecurityUtil app = new SecurityUtil();
        Map<String, Object> keyMap;
        String input = "10001eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        try {
            keyMap = app.initKey();
            String publicKey = app.generatePublicKeyStr(keyMap);
            System.out.println("公钥------------------");
            System.out.println(publicKey);
            System.out.println("length: " + publicKey.length());

            String privateKey = app.generatePrivateKeyStr(keyMap);
            System.out.println("私钥------------------");
            System.out.println(privateKey);
            System.out.println("length: " + privateKey.length());

            System.out.println("测试可行性-------------------");
            System.out.println("明文=======" + input);
            System.out.println("length: " + input.length());

            String message = "a=1&b=2&c=3";
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            String sign = app.sign(data, (PrivateKey) keyMap.get(SecurityUtil.PRIVATE_KEY));
            System.err.println(sign);

            String sign1 = app.sign(data, privateKey);
            System.err.println(sign1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
