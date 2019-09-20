package org.zx.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanLocator implements BeanFactoryAware {
    private static BeanFactory beanFactory; //BEAN工厂

    private static final Comparator comparatorByOrder = Comparator.comparing(o -> {
        Order annotation = o.getClass().getAnnotation(Order.class);
        return annotation == null ? Ordered.LOWEST_PRECEDENCE : annotation.value();
    });

    @Override
    public void setBeanFactory(BeanFactory f) throws BeansException {
        this.beanFactory = f;
    }

    /**
     * 根据bean的名字找bean的对象
     *
     * @param name
     * @return
     */
    public static <T> T findBeanByName(String name) {
        T bean = null;
        try{
            bean = (T) beanFactory.getBean(name);
        }catch (Exception e){

        }
        return bean;
    }

    /**
     * 根据bean的class来找bean，若bean配置的Component注解里有value属性，则按照value属性进行查询bean，否则按照类名首字母小写来查询bean
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T findBeanByClass(Class<T> tClass){
        String beanName = BeanLocator.classToBeanName(tClass);
        return (T) BeanLocator.findBeanByName(beanName);
    }

    public static <T> T findBean(Class<T> tClass){
        T bean = beanFactory.getBean(tClass);
        return bean;
    }

    /**
     * 根据接口类型和bean名字关键字过滤，找到一个实现类即可
     * 此方法可能会找出多个，但是业务上要保证只能出现一个
     * @param type
     * @param beanKey
     * @param <T>
     * @return
     */
    public static <T> T findBeanByKey(Class<T> type, String beanKey){
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream()
                             .filter(entry -> entry.getKey().contains(beanKey))
                             .map(Map.Entry::getValue)
                             .findFirst().get();
    }

    /**
     * 根据接口类型，找到一个实现类即可
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T findBeanByType(Class<T> type){
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream()
                             .map(Map.Entry::getValue)
                             .findFirst().get();
    }

    /**
     * 根据接口类型和bean名字关键字过滤，找到一个实现类即可
     * 此方法可能会找出多个，但是业务上要保证只能出现一个
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T findBeanByKey(Class<T> type, Enum e){
        return findBeanByKey(type, e.name());
    }

    /**
     *根据接口类型找到下面所有的实现类
     * @param type
     * @param <T>
     * @return
     */
    private static <T extends Object> Map<String, T> beanOfTypeIncludingAncestors(Class<T> type) {
        return beanFactory instanceof ListableBeanFactory ?
                    BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory) beanFactory, type):new HashMap<>();
    }

    /**
     * 根据接口类型找到下面所有的实现类
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> getAllBeans(Class<T> type) {
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return new ArrayList<>(map.values());
    }

    /**
     * 直接根据类型找到对应的bean
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllBeans(List<Class<T>> types) {
        return BeanLocator.classStreamToBeanList(types.stream());
    }

    /**
     * 直接根据类型找到对应的bean
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllBeans(Class<T> ... types) {
        return BeanLocator.classStreamToBeanList(Arrays.stream(types));
    }



    /**
     * 根据接口类型找到下面所有的实现类, 按照@org.springframework.core.annotation.Order 注解 排序, value越小 优先级越高,
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> getAllBeansOrdered(Class<T> type) {
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream().map(Map.Entry::getValue).sorted(Comparator.comparing(o -> {
                    Order annotation = o.getClass().getAnnotation(Order.class);
                    return annotation.value();
                }))
                                      .collect(Collectors.toList());
    }

    /**
     * 根据接口类型找到下面所有的实现类, 按照@org.springframework.core.annotation.Order 注解 排序, value越小 优先级越高,
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> getAllBeansOrdered(Class<T> type, String beanKey) {
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream()
                             .filter(entry -> entry.getKey().contains(beanKey))
                .sorted(Comparator.comparing(o -> {
                    Order annotation = o.getClass().getAnnotation(Order.class);
                    return annotation.value();
                }))
                             .map(Map.Entry::getValue)
                             .collect(Collectors.toList());
    }

    /**
     * 根据接口类型找到下面所有的实现类, 按照@org.springframework.core.annotation.Order 注解 排序, value越小 优先级越高,
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> getAllBeansOrdered(Class<T> type, Enum e) {
        return BeanLocator.getAllBeansOrdered(type, e.name());
    }

    /**
     * 根据接口类型和bean名字关键字过滤，找到下面所有的实现类
     * @param type
     * @param beanKey
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllBeans(Class<T> type, String beanKey){
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream()
                             .filter(entry -> entry.getKey().contains(beanKey))
                             .map(Map.Entry::getValue)
                             .collect(Collectors.toList());
    }

    /**
     * 根据接口类型和bean名字关键字过滤，找到下面所有的实现类
     * @param type
     * @param e
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllBeans(Class<T> type, Enum e){
        return BeanLocator.getAllBeans(type, e.name());
    }


    /**
     * 根据接口类型和Predicate<Class> 过滤，找到下面所有的实现类
     * @param type
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllBeans(Class<T> type, Predicate<Class> predicate){
        Map<String, T> map = beanOfTypeIncludingAncestors(type);
        return map.entrySet().stream()
                             .map(Map.Entry::getValue)
                             .filter(value -> predicate.test(value.getClass()))
                             .collect(Collectors.toList());
    }

    /**
     * 根据当前class的Component注解来找Bean的名字，若Component注解里面没有Value属性，则按照类名首字母小写来查询
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> String classToBeanName(Class<T> tClass) {

        Component annotation = tClass.getAnnotation(Component.class);
        Objects.requireNonNull(annotation);

        String beanName = annotation.value();

        return "".equals(beanName)
                ? tClass.getSimpleName().substring(0, 1).toLowerCase() + tClass.getSimpleName().substring(1, tClass.getSimpleName().length())
                : beanName;
    }

    private  static <T> List<T> classStreamToBeanList(Stream<Class<T>> classStream){
        return classStream.map(BeanLocator::classToBeanName)
                          .filter(Objects::nonNull)
                          .map(name -> (T)BeanLocator.findBeanByName(name))
                          .collect(Collectors.toList());
    }
}