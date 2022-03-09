package com.util.security.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES对称加密算法工具类(对秘钥长度要求更高,破解难度更大)
 */
public class AesUtil {

    public static final String ALGORITHM_AES = "AES";

    // 算法/模式/补码方式
    public static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";


    /**
     * 加密
     * @param message message
     * @param key key
     * @return 加密后的字符串
     */
    public static byte[] encrypt(byte[] message, String key) {
        return execute(message, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * @param message message
     * @param key key
     * @return 解密后的字符串
     */
    public static byte[] decrypt(byte[] message, String key) {
        return execute(message, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加密(加密后会执行base64编码)
     * @param message message
     * @param key key
     * @return 加密后字符串
     */
    public static String encryptEncode(byte[] message, String key) {
        byte[] encrypt = encrypt(message, key);
        return new BASE64Encoder().encode(encrypt);
    }

    /**
     * 解密(解密之前对message执行base64解码)
     * @param message message
     * @param key key
     * @return 解密后的字符串
     */
    public static String decryptDecode(String message, String key) {
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(message);
            byte[] decrypt = decrypt(bytes, key);
            return new String(decrypt, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行实际的加密或者解密操作
     * @param message message
     * @param key key
     * @param mode mode
     * @return 加密或解密后的数组
     */
    public static byte[] execute(byte[] message, String key, int mode) {
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM_AES);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
            cipher.init(mode, keySpec);
            return cipher.doFinal(message);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }





}
