package org.zx.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.zx.query.ZXQueryMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
public class ZXMongoQueryMatcherHandler {

    private static Map<ZXQueryMatcher, BiFunction<String, Object, CriteriaDefinition>> map = new HashMap<>();
    private static final String likePatternStr = "^.*%s.*$";
    private static final String notLikePatternStr = "^((?!%s).)*$";

    private static BiFunction<String, Object, Criteria> equalBiFun = (fieldName, fieldValue) -> Criteria.where(fieldName).is(fieldValue);
    private static BiFunction<String, Object, Criteria> notEqualBiFun = (fieldName, fieldValue) -> Criteria.where(fieldName).ne(fieldValue);
    private static BiFunction<String, Object, Criteria> likeBiFun = (fieldName, fieldValue) -> Criteria.where(fieldName).regex(String.format(likePatternStr, fieldValue));
    private static BiFunction<String, Object, Criteria> notLikeBiFun = (fieldName, fieldValue) -> Criteria.where(fieldName).regex(String.format(notLikePatternStr, fieldValue));



    static {
        map.put(ZXQueryMatcher.equal, (fieldName, fieldValue) -> buildCriteriaDefinition(fieldName, fieldValue, equalBiFun));
        map.put(ZXQueryMatcher.notEqual, (fieldName, fieldValue) -> buildCriteriaDefinition(fieldName, fieldValue, notEqualBiFun));

        map.put(ZXQueryMatcher.like, (fieldName, fieldValue) -> buildCriteriaDefinition(fieldName, fieldValue, likeBiFun));
        map.put(ZXQueryMatcher.notLike, (fieldName, fieldValue) -> buildCriteriaDefinition(fieldName, fieldValue, notLikeBiFun));

        map.put(ZXQueryMatcher.gt, (fieldName, fieldValue) -> Criteria.where(fieldName).gt(fieldValue));
        map.put(ZXQueryMatcher.ge, (fieldName, fieldValue) -> Criteria.where(fieldName).gte(fieldValue));

        map.put(ZXQueryMatcher.lt, (fieldName, fieldValue) -> Criteria.where(fieldName).lt(fieldValue));
        map.put(ZXQueryMatcher.le, (fieldName, fieldValue) -> Criteria.where(fieldName).lte(fieldValue));
    }

    public static BiFunction<String, Object, CriteriaDefinition> getFunByMatcher(ZXQueryMatcher matcher){
        BiFunction<String, Object, CriteriaDefinition> function = map.get(matcher);
        return function;
    }

    private static CriteriaDefinition buildCriteriaDefinition(String fieldName, Object fieldValue, BiFunction<String, Object, Criteria> function){
        if (fieldValue instanceof List){
            List<?> list = (List) fieldValue;
            Criteria[] criteriaArray = list.stream().map(d -> function.apply(fieldName, d)).toArray(Criteria[]::new);
            Criteria criteria = new Criteria().orOperator(criteriaArray);
            return criteria;
        }else {
            return function.apply(fieldName, fieldValue);
        }
    }
}
