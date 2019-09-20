package org.zx.utils;

import java.util.Collection;
import java.util.List;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
public class ListUtils {

    /**
     * 取出集合第一个
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T findFirst(List<T> c){
        return EmptyJudgeUtils.isEmpty(c) ? null : c.get(0);
    }

    /**
     * 取出集合最后一份
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T findLast(List<T> c){
        return EmptyJudgeUtils.isEmpty(c) ? null : c.get(c.size() - 1);
    }

    public static Boolean isSizeOne(Collection c){
        return EmptyJudgeUtils.isNotEmpty(c) && c.size() == 1;
    }
}
