package com.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SecurityUtil {

    public static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String ALGORITHM_RSA = "RSA";

    public static final String ALGORITHM_SHA256withRSA = "SHA256withRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";

    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 字节数组中的内容进行签名加密
     * @param data 需要签名的数据
     * @param privateKey 私钥字符串
     * @return 签名
     */
    public String sign(byte[] data, String privateKey) {
        PrivateKey key = getPrivateKey(privateKey);
        return sign(data, key);
    }

    /**
     * 字节数组的内容进行签名加密
     * @param data 需要签名的数据
     * @param privateKey 私钥字符串
     * @return 签名
     */
    public String sign(byte[] data, PrivateKey privateKey) {
        Signature sign;
        try {
            sign = Signature.getInstance(ALGORITHM_SHA256withRSA);
            sign.initSign(privateKey);
            sign.update(data);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据私钥字符串生成私钥对象
     * @param privateKey 私钥字符串
     * @return 私钥对象
     */
    public PrivateKey getPrivateKey(String privateKey) {
        String content = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        try {
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM_RSA);
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(content)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的秘钥格式", e);
        }
    }

    /**
     * 初始化公私钥
     * @return 公私钥map
     * @throws Exception e
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 生成公钥字符串
     * @param keyMap keyMap
     * @return 公钥字符串
     * @throws Exception e
     */
    public String generatePublicKeyStr(Map<String, Object> keyMap) throws Exception {
        // 得到map中的公钥,转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
        // 转为16进制
        // return bytesToHex(encryptBASE64(key.getEncoded()).getBytes());
    }

    /**
     * 生成私钥字符串
     * @param keyMap keyMap
     * @return 私钥字符串
     * @throws Exception e
     */
    public String generatePrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        // 得到map中的私钥,转为key对象
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
        // 转为16进制
        // return bytesToHex(encryptBASE64(key.getEncoded()).getBytes());
    }

    /**
     * 生成公钥
     * @param key key
     * @return 公钥
     * @throws Exception e
     */
    public PublicKey generatePublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 生成私钥
     * @param key key
     * @return 私钥
     * @throws Exception e
     */
    public PrivateKey generatePrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 将byte[]转换为16进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        //一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for(byte b : bytes) { // 使用除与取余进行转换
            if(b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    /**
     * 编码返回字符串
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
