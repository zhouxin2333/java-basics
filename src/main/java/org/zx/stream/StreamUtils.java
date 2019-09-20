package org.zx.stream;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * Created by zhouxin on 2016/1/5.
 */
public class StreamUtils {

    public static <T> BinaryOperator<T> getFirst(){
        return (item1, item2) -> item1;
    }

    public static <T> BinaryOperator<T> getLast(){
        return (item1, item2) -> item2;
    }

    /**
     * 获取一个数字的Stream，从startNum开始，大小为size
     * 例如：startNum为1，size为3，则stream为1,2,3
     *
     * @param startNum
     * @param size
     * @return
     */
    public static Stream<Integer> getStream(int startNum, int size) {
        return Stream.iterate(startNum, item -> item + 1).limit(size).map(Integer::valueOf);
    }

    /**
     * 获取一个数字的Stream，从0开始，大小为size
     *
     * @param size
     * @return
     */
    public static Stream<Integer> getStream(int size) {
        return getStream(0, size);
    }

    /**
     * 合并任意多个Stream<T>
     * @param streams
     * @param <T>
     * @return
     */
    public static <T> Stream<T> concat(Stream<T>... streams) {
        Stream.Builder<T> builder = Stream.builder();
        Stream.of(streams).forEach(s -> s.forEach(item->builder.add(item)));
        return builder.build();
    }
}
