package org.zx.utils;

import org.zx.exception.ZXError;
import org.zx.exception.ZXException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public class DoJudgeEnvUtils {

    public static void judgeNull(Object object, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> EmptyJudgeUtils.isNull(object), error, params);
    }

    public static void judgeEmpty(String str, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> EmptyJudgeUtils.isEmpty(str), error, params);
    }

    public static void judgeComparable(Supplier<Integer> supplier, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> supplier.get() == 1, error, params);
    }

    public static void judgeArrayEmpty(Object[] array, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> EmptyJudgeUtils.isEmpty(array), error, params);
    }

    public static void judgeCollectionEmpty(Collection collection, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> EmptyJudgeUtils.isEmpty(collection), error, params);
    }

    public static void judgeMapEmpty(Map map, ZXError error, Object ... params){
        DoJudgeEnvUtils.judge(() -> EmptyJudgeUtils.isEmpty(map), error, params);
    }

    public static void judge(Supplier<Boolean> supplier, ZXError error, Object ... params){
        if (supplier.get()){
            if (EmptyJudgeUtils.isEmpty(params)){
                throw new ZXException(error, params);
            }else {
                throw new ZXException(error);
            }
        }
    }
}