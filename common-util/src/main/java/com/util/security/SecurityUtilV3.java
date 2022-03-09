package com.util.security;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class SecurityUtilV3 {

    private static SecurityUtilV3 inst = new SecurityUtilV3();
    private SecretKey key = null;
    private IvParameterSpec iv = null;


    public static void main(String[] args) {
        SecurityUtilV3 inst = SecurityUtilV3.getInst();
        String encString = inst.getEncString("hello,world");
        String desString1 = inst.getDesString(encString);
        System.err.println(encString);
        System.err.println(desString1);
    }


    public static SecurityUtilV3 getInst() {
        return inst;
    }

    private SecurityUtilV3() {
        try {
            DESKeySpec dks = new DESKeySpec("cD#`~LS$I&".getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(dks);
            this.iv = new IvParameterSpec("oeIc%6~)".getBytes());
        } catch (Exception var3) {
        }

    }

    public String getEncString(String str) {
        String ret = "";

        try {
            byte[] byteData = this.getEncCode(str.getBytes("utf-8"));
            // ret = Encodes.encodeUrlSafeBase64(byteData);
            ret = encodeUrlSafeBase64(byteData);
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            Object var10 = null;
        }

        return ret;
    }

    public String getEncString4PW(String str) {
        String ret = "";

        try {
            byte[] byteData = this.getEncCode(str.getBytes("utf-8"));
            // ret = Encodes.encodeBase64(byteData);
            ret = encodeBase64(byteData);
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            Object var10 = null;
        }

        return ret;
    }

    public String getDesString(String str) {
        String ret = "";

        try {
            //byte[] byteData = this.getDesCode(Encodes.decodeBase64(str), str);
            byte[] byteData = this.getDesCode(decodeBase64(str), str);
            ret = new String(byteData, "utf-8");
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            Object var10 = null;
        }

        return ret;
    }

    private byte[] getEncCode(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(1, this.key, this.iv);
            byte[] encryptedData = cipher.doFinal(data);
            return encryptedData;
        } catch (Exception var4) {
            System.err.println("DES算法，加密数据出错|" + var4);
            return null;
        }
    }

    private byte[] getDesCode(byte[] data, String str) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(2, this.key, this.iv);
            byte[] decryptedData = cipher.doFinal(data);
            return decryptedData;
        } catch (Exception var5) {
            System.err.println("DES算法，解密出错|" + str + "|" + var5);
            return null;
        }
    }




    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] decodeHex(String input) throws DecoderException {
        return Hex.decodeHex(input.toCharArray());
    }

    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];

        for(int i = 0; i < input.length; ++i) {
            chars[i] = BASE62[(input[i] & 255) % BASE62.length];
        }

        return new String(chars);
    }


}
