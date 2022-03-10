package com.util.security.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * DES对称加密算法工具类
 */
public class DesUtil {

    public static final String ALGORITHM_DES = "DES";

    public static final String ALGORITHM_DES_MODE = "DES/CBC/PKCS5Padding";


    /**
     * 加密
     * @param message message
     * @param key key
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] message, String key) {
        return execute(message, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * @param message message
     * @param key key
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] message, String key) {
        return execute(message, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 执行实际的加密或者解密操作
     * @param message message
     * @param key key
     * @param mode mode
     * @return 加密或解密后的数组
     */
    public static byte[] execute(byte[] message, String key, int mode) {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        try {
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            // 创建秘钥工厂,获取secretKey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            // Cipher对象完成实际解密工作
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(mode, secretKey, random);
            return cipher.doFinal(message);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


}
