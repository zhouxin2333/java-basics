package org.zx.mongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.Nullable;
import org.zx.query.ZXPage;
import org.zx.query.ZXPageQuery;
import org.zx.service.ZXService;

import java.util.List;
import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/8/2
 */
public interface ZXMongoService<T> extends ZXService<T> {

    T saveSubTableByMonth(T t);

    Optional<T> findOne(@Nullable Query query);

    Optional<T> findOneSubTableByMonth(@Nullable Query query);

    Optional<T> findOneSubTableByLastMonth(@Nullable Query query);

    List<T> findAll(@Nullable Query query);

    List<T> findAllSubTableByMonth(@Nullable Query query);

    <R extends ZXPageQuery> ZXPage<T> findAllSubTableByMonth(@Nullable R query);

    DeleteResult remove(@Nullable Query query);
}
