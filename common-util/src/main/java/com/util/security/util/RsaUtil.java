package com.util.security.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

public class RsaUtil {

    public static final String ALGORITHM_RSA = "RSA";

    public static final int KEY_SIZE = 1024;

    public static final String PUBLIC_KEY = "RSAPublicKey";

    public static final String PRIVATE_KEY = "RSAPrivateKey";

    public static final String PUBLIC_KEY_STR = "RSAPublicKeyStr";

    public static final String PRIVATE_KEY_STR = "RSAPrivateKeyStr";

    public static final String ALGORITHM_SHA256withRSA = "SHA256withRSA";



    public String encryptByPublicKey(String message, String publicKey) throws Exception {
        PublicKey key = generatePublicKey(publicKey);
        return encryptByPublicKey(message, key);
    }

    private String encryptByPublicKey(String message, PublicKey key) throws Exception {
        // 获取加密内容的byte[]
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] results = execute(messageBytes, Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(results);
    }

    public String decryptByPrivateKey(String message, String privateKey) throws Exception {
        PrivateKey key = generatePrivateKey(privateKey);
        return decryptByPrivateKey(message, key);
    }

    private String decryptByPrivateKey(String message, PrivateKey key) throws Exception {
        // 获取加密内容的decode bytes[]
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] decodeBytes = Base64.decodeBase64(messageBytes);
        // RSA解密
        byte[] results = execute(decodeBytes, Cipher.DECRYPT_MODE, key);
        return new String(results);
    }

    public byte[] execute(byte[] data, int mode, Key key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM_RSA);
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化公私钥
     * @return 公私钥map
     * @throws Exception e
     */
    public Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        String publicKeyStr = encryptBASE64(publicKey.getEncoded());
        String privateKeyStr = encryptBASE64(privateKey.getEncoded());
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        keyMap.put(PUBLIC_KEY_STR, publicKeyStr);
        keyMap.put(PRIVATE_KEY_STR, privateKeyStr);
        return keyMap;
    }

    /**
     * 生成公钥
     * @param publicKey publicKey
     * @return 公钥
     * @throws Exception e
     */
    public PublicKey generatePublicKey(String publicKey) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        // byte[] keyBytes = Base64.decodeBase64(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 生成私钥
     * @param privateKey privateKey
     * @return 私钥
     * @throws Exception e
     */
    public PrivateKey generatePrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(privateKey);
        // byte[] keyBytes = Base64.decodeBase64(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PKCS8EncodedKeySpec pkcs8KeySpec  = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(pkcs8KeySpec );
    }

    /**
     * 编码返回字符串
     * @param key 公钥或私钥字节数组
     * @return 字符串公钥或私钥
     * @throws Exception e
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
