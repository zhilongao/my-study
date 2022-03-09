package com.util.security.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法(md5 sha1)
 */
public class DigestUtil {

    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    public static final String ALGORITHM_SHA1 = "SHA-1";

    public static final String ALGORITHM_MD5 = "MD5";

    public static String md5(String message) {
        return digest(ALGORITHM_MD5, message);
    }

    public static String hash1(String message) {
        return digest(ALGORITHM_SHA1, message);
    }

    public static String digest(String algorithm, String message) {
        byte[] hash = digest(algorithm, message.getBytes(StandardCharsets.UTF_8));
        return toHex(hash);
    }

    public static byte[] digest(String algorithm, byte[] message) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return md.digest(message);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

}
