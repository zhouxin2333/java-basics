package org.zx.utils;

import java.util.Collection;
import java.util.Collections;
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

    /**
     * 取出除了第一个之外的剩余的
     * @param c
     * @param <T>
     * @return
     */
    public static <T> List<T> findWithoutFirst(List<T> c){
        return EmptyJudgeUtils.isNotEmpty(c) && c.size() > 1 ?  c.subList(1, c.size()) : Collections.EMPTY_LIST;
    }
}
