package org.zx.stream;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream的一些扩展方法
 *
 * @author zhouxin
 * @since 2017/3/10
 */
public class StreamEx {



    /**
     * 根据key来去重的静态方法
     * 一般这么使用-根据name来过滤数据
     * stream.filter(distinctByKey(Student::getName))
     *
     * 注意：当相同的数据出现时，会保留第一次出现的数据
     *
     * @param keyMapper
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<T, ?> keyMapper){
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return p -> map.putIfAbsent(keyMapper.apply(p), Boolean.TRUE) == null;
    }
}
