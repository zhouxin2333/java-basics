package org.zx.utils;

/**
 * @author zhouxin
 * @since 2019/9/10
 */
public class ArrayUtils {

    public static Boolean isSizeOne(Object[] array){
        return EmptyJudgeUtils.isNotEmpty(array) && array.length == 1;
    }
}
