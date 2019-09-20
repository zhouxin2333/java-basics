package org.zx.service;

import org.springframework.lang.Nullable;
import org.zx.query.ZXPage;
import org.zx.query.ZXPageQuery;
import org.zx.utils.EmptyJudgeUtils;

import java.util.List;
import java.util.Optional;


/**
 * @author zhouxin
 * @since 2019/6/5
 */
public interface ZXService<T> {

    /**
     * 不可能为null，所以不用返回Optional
     * @param t
     * @return
     */
    Optional<T> saveOrUpdate(T t);

    Optional<T> findById(Long id);

    Optional<T> findOne(@Nullable Object query);

    List<T> findAll(@Nullable Object query);

    <R extends ZXPageQuery> ZXPage<T> findAll(@Nullable R query);

    long count(@Nullable Object query);

    default boolean isExist(@Nullable Object query){
        Long count = this.count(query);
        return EmptyJudgeUtils.notNull(count) && count.longValue() > 0;
    }

    // 不存在
    default boolean isNotExist(@Nullable Object query){
        return !this.isExist(query);
    }
}
