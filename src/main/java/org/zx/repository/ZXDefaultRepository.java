package org.zx.repository;

/**
 * @author zhouxin
 * @since 2019/6/5
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ZXDefaultRepository<T> extends
        JpaRepository<T, Long>,
        JpaSpecificationExecutor<T> {
}
