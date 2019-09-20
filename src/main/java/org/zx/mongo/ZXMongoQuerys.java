package org.zx.mongo;

import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.zx.query.ZXQueryFiled;
import org.zx.query.ZXQueryMatcher;
import org.zx.utils.ClassUtils;
import org.zx.utils.EmptyJudgeUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * @author zhouxin
 * @since 2019/6/10
 */
public class ZXMongoQuerys {


    public static Query toQuery(Object obj){

        // 获取当前目标对象的所有fileds，除去静态属性，除去没有包含STQueryFiled注解的属性
        Class<?> targetClass = obj.getClass();
        Field[] targetFields = ClassUtils.getAllFieldsWithRoot(targetClass, ClassUtils.notStaticFieldPredicate, ClassUtils.containAnnotationFunction.apply(ZXQueryFiled.class));

        Query query = new Query();
        if (EmptyJudgeUtils.isEmpty(targetFields)) return new Query();

        Arrays.stream(targetFields)
                .map(field -> ZXMongoQuerys.fieldToCriteria(field, obj))
                .filter(EmptyJudgeUtils::notNull)
                .forEach(query::addCriteria);

        return query;
    }

    private static CriteriaDefinition fieldToCriteria(Field field, Object targetObject) {
        // 获取当前属性值
        String fieldName = field.getName();
        Object fieldValue = ClassUtils.getValueWithoutError(targetObject, fieldName);
        // 若没有属性值，则直接返回
        if (EmptyJudgeUtils.isNull(fieldValue)) return null;

        ZXQueryFiled annotation = field.getAnnotation(ZXQueryFiled.class);
        ZXQueryMatcher operator = annotation.operator();
        String annotationName = annotation.name();
        if (EmptyJudgeUtils.isNotEmpty(annotationName)){
            fieldName = annotationName;
        }

        BiFunction<String, Object, CriteriaDefinition> funByMatcher = ZXMongoQueryMatcherHandler.getFunByMatcher(operator);
        CriteriaDefinition criteriaDefinition = funByMatcher.apply(fieldName, fieldValue);
        return criteriaDefinition;
    }
}
