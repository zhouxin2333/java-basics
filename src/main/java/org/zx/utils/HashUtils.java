package org.zx.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    /*
     *md5 algorithm 
     */

    public static final String MD5 = "MD5";
    /*
     *sha1 algorithm 
     */
    public static final String SHA1 = "SHA1";

    private static final String HEX_STRING = "0123456789abcdef";
    private static final char[] HEX_DIGITS = HEX_STRING.toCharArray();


    /*========================MD5 start=========================*/

    public static String MD5(byte[] array){
        byte[] bytes = HashUtils.encode(HashUtils.MD5,array);
        return HashUtils.toHexString(bytes);
    }

    public static String MD5(String str) {
        byte[] bytes = HashUtils.encode(HashUtils.MD5, str.getBytes());
        return HashUtils.toHexString(bytes);
    }

    public static String MD5(String str, String charset) {
        byte[] bytes = null;
        try {
            bytes = HashUtils.encode(HashUtils.MD5, str.getBytes(charset));
            return HashUtils.toHexString(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*======================MD5 end=============================*/
    /*======================SHA1 start==========================*/
    public static String SHA1(byte[] array){
        byte[] bytes = HashUtils.encode(HashUtils.SHA1,array);
        return HashUtils.toHexString(bytes);
    }

    public static String SHA1(String str) {
        byte[] bytes = HashUtils.encode(HashUtils.SHA1, str.getBytes());
        return HashUtils.toHexString(bytes);
    }

    public static String SHA1(String str, String charset) {
        byte[] bytes = null;
        try {
            bytes = HashUtils.encode(HashUtils.SHA1, str.getBytes(charset));
            return HashUtils.toHexString(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*======================SHA1 end============================*/
    public static byte[] encode(String algorithm, byte[] bytes) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param bytes
     * @return
     */
    private static String toHexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}
