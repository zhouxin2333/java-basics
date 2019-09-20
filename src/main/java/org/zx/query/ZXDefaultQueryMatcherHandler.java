package org.zx.query;

import org.springframework.data.jpa.domain.Specification;
import org.zx.constants.CommonConstants;
import org.zx.utils.ListUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
public class ZXDefaultQueryMatcherHandler {

    private static Map<ZXQueryMatcher, BiFunction<String, Object, Specification>> map = new HashMap<>();

    static {
        map.put(ZXQueryMatcher.equal, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(getPath(root, fieldName), fieldValue));
        map.put(ZXQueryMatcher.notEqual, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(getPath(root, fieldName), fieldValue));

        map.put(ZXQueryMatcher.like, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.like(getPath(root, fieldName), "%" + fieldValue + "%"));
        map.put(ZXQueryMatcher.notLike, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.notLike(getPath(root, fieldName), "%" + fieldValue + "%"));

        map.put(ZXQueryMatcher.gt, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(getPath(root, fieldName), (Comparable) fieldValue));
        map.put(ZXQueryMatcher.ge, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(getPath(root, fieldName), (Comparable) fieldValue));

        map.put(ZXQueryMatcher.lt, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(getPath(root, fieldName), (Comparable) fieldValue));
        map.put(ZXQueryMatcher.le, (fieldName, fieldValue) -> (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(getPath(root, fieldName), (Comparable) fieldValue));
    }

    public static BiFunction<String, Object, Specification> getFunByMatcher(ZXQueryMatcher matcher){
        BiFunction<String, Object, Specification> function = map.get(matcher);
        return function;
    }

    private static final Pattern pattern = Pattern.compile("\\.");
    private static Path getPath(Root root, String fieldName){
        if (fieldName.contains("\\.")){
            List<String> fieldNames = pattern.splitAsStream(fieldName).collect(Collectors.toList());

            String firstFieldName = ListUtils.findFirst(fieldNames);
            Join joinTable = fieldNames.stream()
                    .skip(1)
                    .limit(fieldNames.size() - 2)
                    .reduce(root.join(firstFieldName, JoinType.LEFT), (join, s) -> join.join(s, JoinType.LEFT), CommonConstants.getLeftBinaryOperator);

            String lastFieldName = ListUtils.findLast(fieldNames);
            return joinTable.get(lastFieldName);
        }else {
            return root.get(fieldName);
        }
    }
}
