package org.zx.utils;

import org.springframework.core.ResolvableType;

/**
 * 泛型工具
 * @author zhouxin
 * @since 2019/6/17
 */
public class GenericUtils {

    /**
     * 获取当前类的父类的第一个泛型类型
     * @param tClass
     * @return
     */
    public static Class getSuperTypeFirstGeneric(Class tClass){
        Class<?> resolve = ResolvableType.forClass(tClass).getSuperType().getGeneric(0).resolve();
        return resolve;
    }

    /**
     * 获取当前类的父类的第二个泛型类型
     * @param tClass
     * @return
     */
    public static Class getSuperTypeSecondGeneric(Class tClass){
        Class<?> resolve = ResolvableType.forClass(tClass).getSuperType().getGeneric(1).resolve();
        return resolve;
    }

    /**
     * 获取当前类的父类的第三个泛型类型
     * @param tClass
     * @return
     */
    public static Class getSuperTypeThirdGeneric(Class tClass){
        Class<?> resolve = ResolvableType.forClass(tClass).getSuperType().getGeneric(2).resolve();
        return resolve;
    }

    /**
     * 获取当前类的父类的第一个泛型类型
     * @param tClass
     * @return
     */
    public static <T> T getSuperTypeFirstGenericObj(Class tClass){
        Class<T> resolve = GenericUtils.getSuperTypeFirstGeneric(tClass);
        T o = ClassUtils.initSimple(resolve);
        return o;
    }

    /**
     * 获取当前类的父类的第二个泛型类型
     * @param tClass
     * @return
     */
    public static <T> T getSuperTypeSecondGenericObj(Class tClass){
        Class<T> resolve = GenericUtils.getSuperTypeSecondGeneric(tClass);
        T o = ClassUtils.initSimple(resolve);
        return o;
    }


    /**
     * 获取当前类的父类的第三个泛型类型
     * @param tClass
     * @return
     */
    public static <T> T getSuperTypeThirdGenericObj(Class tClass){
        Class<T> resolve = GenericUtils.getSuperTypeThirdGeneric(tClass);
        T o = ClassUtils.initSimple(resolve);
        return o;
    }
}
