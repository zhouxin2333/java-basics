package org.zx.mongo;

/**
 * @author zhouxin
 * @since 2019/6/5
 */

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ZXMongoRepository<T> extends MongoRepository<T, Long> {
}
