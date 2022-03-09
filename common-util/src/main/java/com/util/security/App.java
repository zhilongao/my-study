package com.util.security;

import com.util.security.util.AesUtil;
import com.util.security.util.DesUtil;
import com.util.security.util.DigestUtil;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // testDes();
        // testAes();
        testSha1();
        testMd5();
        System.err.println("*********");
        System.err.println();

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


    public static void testDes() {
        String key = "2137183&&&&!@!@#!#@#!@#!@##";
        String message = "hello,jack110101";

        byte[] encrypt = DesUtil.encrypt(message.getBytes(StandardCharsets.UTF_8), key);
        System.err.println("加密后:" + new String(encrypt));

        byte[] decrypt = DesUtil.decrypt(encrypt, key);
        System.err.println("解密后:" + new String(decrypt));
    }

    public static void testAes() {
        String key = "12922222222222221292222222222222";
        String message = "hekllo,worrdfs9123123";
        byte[] encrypt = AesUtil.encrypt(message.getBytes(StandardCharsets.UTF_8), key);
        System.err.println("加密后:" + new String(encrypt));

        byte[] decrypt = AesUtil.decrypt(encrypt, key);
        System.err.println("解密后:" + new String(decrypt));


        String message2 = "执行一段已知的代码快haha";
        String s1 = AesUtil.encryptEncode(message2.getBytes(StandardCharsets.UTF_8), key);
        System.err.println("加密后:" + s1);

        String s2 = AesUtil.decryptDecode(s1, key);
        System.err.println("解密后:" + s2);
    }

    public static void testSha1() {
        String message = "23213wweqhwekqwhewqkehwqkewq213213";
        String s = DigestUtil.hash1(message);
        System.err.println(s);
    }

    public static void testMd5() {
        String message = "23213wweqhwekqwhewqkehwqkewq213213";
        String s = DigestUtil.md5(message);
        System.err.println(s);
    }


}
