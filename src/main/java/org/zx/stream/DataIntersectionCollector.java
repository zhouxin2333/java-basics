package org.zx.stream;

import org.zx.utils.EmptyJudgeUtils;
import org.zx.utils.ListUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据集合交集筛选器
 *
 * @author zhouxin
 * @since 2017/5/5
 */
public class DataIntersectionCollector {

    /**
     * 找出所有的时间集合的公共部分
     * @param datas
     * @return
     */
    public static List<LocalDate> collectToLocalDate(List<List<String>> ... datas){
        return collect(LocalDate::parse, datas);
    }

    /**
     * 找出所有的字符串集合的公共部分
     * @param datas
     * @return
     */
    public static List<String> collect(List<List<String>> ... datas){
        return collect(Function.identity(), datas);
    }

    /**
     * 找出所有的字符串集合的公共部分
     * @param datas
     * @return
     */
    public static List<String> collectByLocalDate(List<List<LocalDate>> ... datas){
        return collect(LocalDate::toString, datas);
    }

    /**
     * 找出所有的数据集合的公共部分
     * @param datas
     * @return
     */
    public static <T, R> List<R> collect(Function<T, R> function, List<List<T>> ... datas){
        List<List<T>> dataList = Arrays.stream(datas).filter(EmptyJudgeUtils::notNull).flatMap(List::stream).collect(Collectors.toList());
        if (EmptyJudgeUtils.isEmpty(dataList)) return Collections.EMPTY_LIST;
        if (ListUtils.isSizeOne(dataList)) return ListUtils.findFirst(dataList).stream().map(function).collect(Collectors.toList());
        return collect(ListUtils.findFirst(dataList), function, ListUtils.findWithoutFirst(dataList));
    }

    /**
     * 找出所有的数据集合的公共部分
     * @param first
     * @return
     */
    public static <T, R> List<R> collect(List<T> first, Function<T, R> function, List<List<T>> dataList){
        return collect(first, function, dataList.toArray(new List[]{}));
    }

    /**
     * 找出所有的数据集合的公共部分
     * @param first
     * @param datas
     * @return
     */
    public static <T, R> List<R> collect(List<T> first, Function<T, R> function, List<T> ... datas){
        if (EmptyJudgeUtils.isEmpty(first)) return Collections.EMPTY_LIST;
        if (EmptyJudgeUtils.isEmpty(datas)) return first.stream().map(function).collect(Collectors.toList());
        return first.stream().filter(data -> allMatch(data, datas)).map(function).collect(Collectors.toList());
    }

    private static <T> boolean allMatch(T data, List<T>[] dataArr) {
        if (EmptyJudgeUtils.isEmpty(dataArr)) return true;
        return Arrays.stream(dataArr).allMatch(dataList -> dataList.contains(data));
    }


}
