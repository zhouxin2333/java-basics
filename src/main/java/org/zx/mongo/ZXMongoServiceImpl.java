package org.zx.mongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.zx.query.ZXDefaultQuerys;
import org.zx.query.ZXPage;
import org.zx.query.ZXPageQuery;
import org.zx.utils.EmptyJudgeUtils;
import org.zx.utils.GenericUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
public class ZXMongoServiceImpl<T> implements ZXMongoService<T> {

    @Autowired
    protected ZXMongoRepository<T> mongoRepository;
    @Autowired
    protected MongoTemplate mongoTemplate;
    // 当前泛型的类型
    private Class<T> tClass;


    @Override
    public T saveSubTableByMonth(T t) {
        String collectionNameYearMonth = SubTableUtils.getCollectionNameYearMonth(this.getCurrentClass());
        return mongoTemplate.save(t, collectionNameYearMonth);
    }

    @Override
    public Optional<T> saveOrUpdate(T t) {
        return Optional.ofNullable(mongoRepository.save(t));
    }

    @Override
    public Optional<T> findById(Long id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Optional<T> findOne(Object query) {
        Query queryObj = ZXMongoQuerys.toQuery(query);
        T data = mongoTemplate.findOne(queryObj, this.getCurrentClass());
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<T> findOne(Query query) {
        T data = mongoTemplate.findOne(query, this.getCurrentClass());
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<T> findOneSubTableByMonth(Query query) {
        String collectionNameYearMonth = SubTableUtils.getCollectionNameYearMonth(this.getCurrentClass());
        T data = mongoTemplate.findOne(query, this.getCurrentClass(), collectionNameYearMonth);
        return Optional.ofNullable(data);
    }

    @Override
    public Optional<T> findOneSubTableByLastMonth(Query query) {
        String collectionNameYearMonth = SubTableUtils.getCollectionNameYearMonth4LastMonth(this.getCurrentClass());
        T data = mongoTemplate.findOne(query, this.getCurrentClass(), collectionNameYearMonth);
        return Optional.ofNullable(data);
    }

    @Override
    public List<T> findAll(Object query) {
        Query queryObj = ZXMongoQuerys.toQuery(query);
        List<T> list = mongoTemplate.find(queryObj, this.getCurrentClass());
        return list;
    }

    @Override
    public List<T> findAll(Query query) {
        List<T> list = mongoTemplate.find(query, this.getCurrentClass());
        return list;
    }

    @Override
    public List<T> findAllSubTableByMonth(Query query) {
        String collectionNameYearMonth = SubTableUtils.getCollectionNameYearMonth(this.getCurrentClass());
        List<T> list = mongoTemplate.find(query, this.getCurrentClass(), collectionNameYearMonth);
        return list;
    }

    @Override
    public long count(Object query) {
        Query queryObj = ZXMongoQuerys.toQuery(query);
        long count = mongoTemplate.count(queryObj, this.getCurrentClass());
        return count;
    }

    @Override
    public boolean isExist(Object query) {
        Query queryObj = ZXMongoQuerys.toQuery(query);
        boolean exists = mongoTemplate.exists(queryObj, this.getCurrentClass());
        return exists;
    }

    @Override
    public <R extends ZXPageQuery> ZXPage<T> findAll(R query) {
        ZXPage<T> ZXPage = this.findAll(query, mongoTemplate.getCollectionName(this.getCurrentClass()));
        return ZXPage;
    }

    @Override
    public <R extends ZXPageQuery> ZXPage<T> findAllSubTableByMonth(R query) {
        ZXPage<T> ZXPage = this.findAll(query, SubTableUtils.getCollectionNameYearMonth(this.getCurrentClass()));
        return ZXPage;
    }

    private <R extends ZXPageQuery> ZXPage<T> findAll(R query, String collectionName) {
        Query queryObj = ZXMongoQuerys.toQuery(query);
        Pageable pageable = ZXDefaultQuerys.toPageable(query);
        queryObj.with(pageable);

        // 查询总数
        long count = mongoTemplate.count(queryObj, this.getCurrentClass(), collectionName);
        // 查询数据
        List<T> list = mongoTemplate.find(queryObj, this.getCurrentClass(), collectionName);

        Page<T> page = PageableExecutionUtils.getPage(list, pageable, () -> count);
        ZXPage<T> zxPage = ZXPage.of(page);
        return zxPage;
    }

    @Override
    public DeleteResult remove(Query query) {
        DeleteResult deleteResult = mongoTemplate.remove(query, this.getCurrentClass());
        return deleteResult;
    }

    private Class<T> getCurrentClass(){
        if (EmptyJudgeUtils.notNull(this.tClass)) return this.tClass;
        Class<T> superTypeFirstGeneric = GenericUtils.getSuperTypeFirstGeneric(this.getClass());
        this.tClass = superTypeFirstGeneric;
        return tClass;
    }
}
