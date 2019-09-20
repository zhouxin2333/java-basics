package org.zx.utils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2019/6/10
 */
public class StringUtils {

    /**
     * 首字符大写
     *
     * @param str
     * @return
     */
    public static String toFirstUpCase(String str) {
        return toSomeCase(str, String::toUpperCase);
    }

    /**
     * 首字符小写
     *
     * @param str
     * @return
     */
    public static String toFirstLowerCase(String str) {
        return toSomeCase(str, String::toLowerCase);
    }

    private static String toSomeCase(String str, Function<String, String> fun) {
        return fun.apply(str.substring(0, 1)) + str.substring(1, str.length());
    }

    public static String stackTraceFormat(Throwable e){
        Stream<String> stringStream = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString);
        String errorMsg = Stream.concat(Stream.of(e.toString()), stringStream).collect(Collectors.joining("\n"));
        return errorMsg;
    }
}
