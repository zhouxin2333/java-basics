package org.zx.constants;

import org.springframework.data.jpa.domain.Specification;
import org.zx.query.ZXPage;

/**
 * @author zhouxin
 * @since 2019/6/5
 */
public interface SpecificationConstants {

    Specification<ZXPage> emptySpec = (root, query, criteriaBuilder) -> criteriaBuilder.and();

    Specification distinctSpec = (root, query, cb) -> {
        query.distinct(true);
        return cb.and();
    };
}
