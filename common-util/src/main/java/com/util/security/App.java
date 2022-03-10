package com.util.security;

import com.util.security.util.AesUtil;
import com.util.security.util.DesUtil;
import com.util.security.util.DigestUtil;
import com.util.security.util.RsaUtil;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        testDes();
        testAes();
        testSha1();
        testMd5();
        testRsa();
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


    public static void testRsa() {
        String message = "hello,world10000002222";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        RsaUtil util = new RsaUtil();
        try {
            Map<String, Object> keyMap = util.initKey();
            String publicKeyStr = (String)keyMap.get(RsaUtil.PUBLIC_KEY_STR);
            String privateKeyStr = (String)keyMap.get(RsaUtil.PRIVATE_KEY_STR);
            PublicKey publicKey = (PublicKey)keyMap.get(RsaUtil.PUBLIC_KEY);
            PrivateKey privateKey = (PrivateKey)keyMap.get(RsaUtil.PRIVATE_KEY);

            System.err.println("------公钥--------");
            System.err.println(publicKeyStr);
            System.err.println("\n------私钥--------");
            System.err.println(privateKeyStr);


            String encrypt = util.encryptByPublicKey(message, publicKeyStr);
            System.err.println(encrypt);
            String decrypt = util.decryptByPrivateKey(encrypt, privateKeyStr);
            System.err.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
