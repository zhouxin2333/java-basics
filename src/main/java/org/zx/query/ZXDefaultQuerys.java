package org.zx.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.zx.constants.SpecificationConstants;
import org.zx.utils.ClassUtils;
import org.zx.utils.EmptyJudgeUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2019/6/5
 */
public class ZXDefaultQuerys {

    public static Specification toSpec(Object obj){

        // 获取当前目标对象的所有fileds，除去静态属性，除去没有包含STQueryFiled注解的属性
        Class<?> targetClass = obj.getClass();
        Field[] targetFields = ClassUtils.getAllFieldsWithRoot(targetClass, ClassUtils.notStaticFieldPredicate, ClassUtils.containAnnotationFunction.apply(ZXQueryFiled.class));

        if (EmptyJudgeUtils.isEmpty(targetFields)) return SpecificationConstants.emptySpec;

        Optional<Specification> specificationOptional = Arrays.stream(targetFields)
                .map(field -> ZXDefaultQuerys.fieldToSpec(field, obj))
                .filter(EmptyJudgeUtils::notNull)
                .reduce(Specification::and);

        return specificationOptional.orElse(SpecificationConstants.emptySpec);
    }

    private static Specification fieldToSpec(Field field, Object obj){
        // 获取当前属性值
        String fieldName = field.getName();
        Object fieldValue = ClassUtils.getValueWithoutError(obj, fieldName);
        // 若没有属性值，则直接返回null
        if (EmptyJudgeUtils.isNull(fieldValue)) return null;

        ZXQueryFiled annotation = field.getAnnotation(ZXQueryFiled.class);
        ZXQueryMatcher operator = annotation.operator();
        String annotationName = annotation.name();
        if (EmptyJudgeUtils.isNotEmpty(annotationName)){
            fieldName = annotationName;
        }
        BiFunction<String, Object, Specification> funByMatcher = ZXDefaultQueryMatcherHandler.getFunByMatcher(operator);
        Specification specification = Specification.where(funByMatcher.apply(fieldName, fieldValue));

        return specification;
    }

    public static Pageable toPageable(ZXPageQuery query){
        PageRequest request = null;
        List<ZXPageOrder> orders = query.getOrders();
        int pageIndex = query.getPageIndex() - 1;
        if (EmptyJudgeUtils.isEmpty(orders)){
            request = PageRequest.of(pageIndex, query.getPageSize());
        }else {
            List<Sort.Order> sortOrders = orders.stream().map(ZXDefaultQuerys::toOrder).collect(Collectors.toList());
            Sort sort = Sort.by(sortOrders);
            request = PageRequest.of(pageIndex, query.getPageSize(), sort);
        }
        return request;
    }

    private static Sort.Order toOrder(ZXPageOrder pageOrder) {
        String property = pageOrder.getProperty();
        ZXPageOrder.Direction direction = pageOrder.getDirection();
        if (ZXPageOrder.Direction.ASC.equals(direction)){
            return Sort.Order.asc(property);
        }else {
            return Sort.Order.desc(property);
        }
    }
}
