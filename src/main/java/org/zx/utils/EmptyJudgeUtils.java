package org.zx.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class EmptyJudgeUtils {

    public static boolean isNull(Object obj) {
        return obj == null;
    }
    public static boolean notNull(Object obj) {
        return obj != null;
    }

    public static boolean anyNull(Object ... objs){
        return Arrays.stream(objs).anyMatch(Objects::isNull);
    }
    public static boolean anyNonNull(Object ... objs){
        return Arrays.stream(objs).anyMatch(Objects::nonNull);
    }

    public static boolean allNull(Object ... objs){
        return Arrays.stream(objs).allMatch(Objects::isNull);
    }
    public static boolean allNonNull(Object ... objs){
        return Arrays.stream(objs).allMatch(Objects::nonNull);
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }
    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty();
    }
    public static boolean isNotEmpty(Map m) {
        return !isEmpty(m);
    }

    public static boolean isEmpty(final Object[] target) {
        return target == null || target.length <= 0;
    }
    public static boolean isNotEmpty(final Object[] target) {
        return !isEmpty(target);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static <T> T getOrDefault(T obj, T defaultObj) {
        return obj != null ? obj : defaultObj;
    }
}