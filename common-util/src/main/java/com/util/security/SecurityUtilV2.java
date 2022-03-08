package com.util.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class SecurityUtilV2 {

    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    public static String encryptV1(String content, String key) {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = new SecureRandom(key.getBytes(ENCODING));
            kGen.init(128, random);
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            byte[] byteContent = content.getBytes(ENCODING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteResult = cipher.doFinal(byteContent);
            StringBuilder sb = new StringBuilder();
            for (byte b : byteResult) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) hex = '0' + hex;
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

    public static String encryptV2(String content, String key) {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key.getBytes(ENCODING));
            kGen.init(128, random);
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            byte[] byteContent = content.getBytes(ENCODING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteResult = cipher.doFinal(byteContent);
            StringBuilder sb = new StringBuilder();
            for (byte b : byteResult) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) hex = '0' + hex;
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param key 解密的密钥
     * @return
     */
    public static String decryptV1(String content, String key) {
        if (content.length() < 1) return null;
        byte[] byteResult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteResult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            kGen.init(128, new SecureRandom(key.getBytes(ENCODING)));
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteResult);
            return new String(result, ENCODING);
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

    public static String decryptV2(String content, String key) {
        if (content.length() < 1) return null;
        byte[] byteResult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteResult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key.getBytes(ENCODING));
            kGen.init(128, random);
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteResult);
            return new String(result, ENCODING);
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

}
