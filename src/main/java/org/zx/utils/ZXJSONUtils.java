package org.zx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author zhouxin
 * @since 2019/5/31
 */
public class ZXJSONUtils {

    private static ObjectMapper mapper = new ObjectMapper();


    public static String toJSONString(Object o){
        String s = null;
        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T parseObject(String s, Class<T> tClass){
        try {
            T t = mapper.readValue(s, tClass);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isJSONString(String s){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(s);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
