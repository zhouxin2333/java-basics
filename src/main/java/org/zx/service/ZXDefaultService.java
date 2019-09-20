package org.zx.service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.zx.query.ZXDefaultQuerys;
import org.zx.query.ZXPage;
import org.zx.query.ZXPageQuery;
import org.zx.utils.EmptyJudgeUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
public interface ZXDefaultService<T> extends ZXService<T> {

    Optional<T> findOne(@Nullable Specification<T> spec);
    default Optional<T> findOne(@Nullable Object query){
        Specification specification = ZXDefaultQuerys.toSpec(query);
        return this.findOne(specification);
    }

    List<T> findAll(@Nullable Specification<T> spec);
    default List<T> findAll(@Nullable Object query){
        Specification specification = ZXDefaultQuerys.toSpec(query);
        return this.findAll(specification);
    }

    ZXPage<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
    default <R extends ZXPageQuery> ZXPage<T> findAll(@Nullable R query){
        Specification specification = ZXDefaultQuerys.toSpec(query);
        Pageable pageable = ZXDefaultQuerys.toPageable(query);
        return this.findAll(specification, pageable);
    }

    long count(@Nullable Specification<T> spec);
    default long count(@Nullable Object query){
        Specification specification = ZXDefaultQuerys.toSpec(query);
        return this.count(specification);
    }

    // 存在
    default boolean isExist(Specification<T> specification){
        Long count = this.count(specification);
        return EmptyJudgeUtils.notNull(count) && count.longValue() > 0;
    }
    default boolean isNotExist(Specification<T> specification){
        return !this.isExist(specification);
    }
}
