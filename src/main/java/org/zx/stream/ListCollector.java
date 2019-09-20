package org.zx.stream;

import org.zx.utils.EmptyJudgeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 集合抓取器
 *
 * @author zhouxin
 * @since 2018/1/5
 */
public class ListCollector {

    /**
     * 取集合交集
     * @param lists
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(List<List<T>> lists){
        if (EmptyJudgeUtils.isEmpty(lists)) return new ArrayList<>();
        return ListCollector.intersection(lists.stream());
    }

    /**
     * 取集合交集
     * @param listStream
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(Stream<List<T>> listStream){
        return listStream.filter(EmptyJudgeUtils::isNotEmpty).reduce((list1, list2) -> {
                list1.retainAll(list2);
                return list1;
        }).orElse(new ArrayList<>());
    }

    /**
     * 集合取交集
     * @param lists
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(List<T> ... lists){
        if (EmptyJudgeUtils.isEmpty(lists)) return new ArrayList<>();
        return ListCollector.intersection(Arrays.stream(lists));
    }
}
