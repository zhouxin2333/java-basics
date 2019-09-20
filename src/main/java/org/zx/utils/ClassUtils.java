package org.zx.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassUtils {

    public static final Predicate<Field> notStaticFieldPredicate = field -> !Modifier.isStatic(field.getModifiers());
    public static final Function<Class, Predicate<Field>> containAnnotationFunction =
            annotationClass -> field -> field.isAnnotationPresent(annotationClass);
    private static final String GETTER = "get";
    private static final String SETTER = "set";


    public static <T> Class<T> parse(String classStr){
        try {
            Class<T> tClass = (Class<T>) Class.forName(classStr);
            return tClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据field名字和目标class获取field字段
     *
     * @param clazz
     * @param name
     * @return
     */
    public static Field getFieldWithRoot(Class<?> clazz, String name) {
        Field[] dFields = getAllFieldsWithRoot(clazz);
        return Stream.of(dFields).filter(field -> field.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * 获取类clazz的所有Field，包括其父类的Field，如果重名，以子类Field为准。无静态属性
     *
     * @param clazz
     * @return Field数组
     */
    public static Field[] getAllFieldsWithRootNoStatic(Class<?> clazz) {
        return getAllFieldsWithRoot(clazz, notStaticFieldPredicate);
    }

    /**
     * 获取类clazz的所有Field，包括其父类的Field，如果重名，以子类Field为准。
     *
     * @param clazz
     * @return Field数组
     */
    public static Field[] getAllFieldsWithRoot(Class<?> clazz) {
        return getAllFieldsWithRoot(clazz, null);
    }

    /**
     * 获取类clazz的所有Field，包括其父类的Field，如果重名，以子类Field为准。
     *
     * @param clazz
     * @return Field数组
     */
    public static Field[] getAllFieldsWithRoot(Class<?> clazz, Predicate<Field> ... predicates) {
        List<Field> fieldList = new ArrayList<Field>();
        Field[] dFields = clazz.getDeclaredFields();
        if (EmptyJudgeUtils.isNotEmpty(dFields)) fieldList.addAll(Arrays.asList(dFields));

        Predicate<Field> fieldPredicate = null;
        if (EmptyJudgeUtils.isNotEmpty(predicates)){
            Predicate<Field> predicate = Arrays.stream(predicates).reduce(Predicate::and).get();
            fieldPredicate = predicate;
            fieldList = fieldList.stream().filter(field -> predicate.test(field)).collect(Collectors.toList());
        }

        // 若父类是Object，则直接返回当前Field列表
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) return fieldList.toArray(new Field[fieldList.size()]);

        // 递归查询父类的field列表
        Field[] superFields = getAllFieldsWithRoot(superClass, fieldPredicate);
        if (EmptyJudgeUtils.isNotEmpty(superFields)) {
            List<Field> superFieldList = filterField(superFields, fieldList, fieldPredicate);
            if (EmptyJudgeUtils.isNotEmpty(superFieldList)){
                fieldList.addAll(superFieldList);
            }
        }

        Field[] result = new Field[fieldList.size()];
        fieldList.toArray(result);
        return result;
    }

    private static List<Field> filterField(Field[] superFields, List<Field> fieldList, Predicate<Field> predicate) {
        return Stream.of(superFields).filter(field -> !fieldList.contains(field))
                .filter(field -> predicate == null || predicate.test(field))
                .collect(Collectors.toList());
    }

    public static <R> R initSimple(Class<R> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据请求对象和属性的name来用get方法获取对应的属性值
     *
     * @param obj
     * @param name
     * @return
     */
    public static Object getValueWithoutError(Object obj, String name) {
        Object value = null;
        try {
            Method method = obj.getClass().getMethod(GETTER + StringUtils.toFirstUpCase(name));
            value = method.invoke(obj);
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 把找到的具体值设置到目标对象中
     *
     * @param name
     * @param value
     * @param targetObj
     * @param <T>
     */
    public static <T> void setValue(String name, Object value, Object targetObj) {
        try {
            Class fieldClass = getFieldWithRoot(targetObj.getClass(), name).getType();
            Method method = targetObj.getClass().getMethod(SETTER + StringUtils.toFirstUpCase(name), fieldClass);
            method.invoke(targetObj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}