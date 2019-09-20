package org.zx.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
public class ExceptionFormatUtils {

    public static String stackTraceFormat(Throwable e){
        Stream<String> stringStream = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString);
        String errorMsg = Stream.concat(Stream.of(e.toString()), stringStream).collect(Collectors.joining("\n"));
        return errorMsg;
    }
}
