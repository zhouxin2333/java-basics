package org.zx.utils;

/**
 * @author zhouxin
 * @since 2019/8/1
 */
public class ByteArrayUtils {

    // low的方法，以后改改？看看ByteBuf
    public static byte[] mergeByteArray(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
}
