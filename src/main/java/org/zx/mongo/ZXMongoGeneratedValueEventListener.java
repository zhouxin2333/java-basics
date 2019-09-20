package org.zx.mongo;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;
import org.zx.utils.EmptyJudgeUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

public class ZXMongoGeneratedValueEventListener<T> extends AbstractMongoEventListener<T> {

    @Resource
    private MongoTemplate mongoTemplate;

    public void onBeforeConvert(BeforeConvertEvent<T> event) {
        T source = event.getSource();
        if(source != null) {
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
                    if (field.isAnnotationPresent(ZXMongoGeneratedValue.class)) {
                        Long id = (Long)field.get(source);
                        // 若不为空，则直接返回
                        if (EmptyJudgeUtils.notNull(id)) return;
                        //设置自增ID
                        field.set(source, getNextId(source.getClass().getSimpleName()));
                    }
                 }
            });
        }
   }

    /**
     * 获取下一个自增ID
     * @author yinjihuan
     * @param collName  集合名
     * @return
     */
    private Long getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
        return seqId.getSeqId();
    }

}