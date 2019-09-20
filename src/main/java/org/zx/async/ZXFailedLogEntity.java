package org.zx.async;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zx.entity.ZXIdEntity;
import org.zx.utils.BeanLocator;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class ZXFailedLogEntity<T, R extends ZXAsyncTaskCoreBusiness<T>> extends ZXIdEntity {

    /**
     * 重试状态
     */
    @Column(name = "retry_status")
    @Enumerated(EnumType.STRING)
    private FailedLogRetryStatus retryStatus = FailedLogRetryStatus.FAILED;

    /**
     * 该重试记录有效与否
     */
    @Column(name = "valid_status")
    @Enumerated(EnumType.STRING)
    private FailedLogValidStatus validStatus = FailedLogValidStatus.VALID;

    /**
     * 重试次数
     */
    @Column(name = "retry_times")
    private Integer retryTimes = Integer.valueOf(0);

    /**
     * 最新一次失败的异常堆栈
     */
    @Column(columnDefinition = "text", name = "error_stack")
    private String errorStack;

    /**
     * 重试成功后的关键id
     */
    @Column(name = "retry_success_key_id")
    private String retrySuccessKeyId;

    /**
     * 参数context的class类
     */
    @Column(name = "context_class")
    private String contextClass;

    /**
     * 参数context的值
     */
    @Column(columnDefinition = "text", name = "context_value")
    private String contextValue;

    /**
     * core bussiness的class类
     */
    @Column(name = "core_business_class")
    private String coreBusinessClass;

    @JSONField(serialize = false)
    @JsonIgnore
    public T getContext(){
        Class<T> targetClass = null;
        try {
            targetClass = (Class<T>) Class.forName(this.contextClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        T context = JSONObject.parseObject(this.contextValue, targetClass);
        return context;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public R getCoreBusiness(){
        Class<R> targetClass = null;
        try {
            targetClass = (Class<R>) Class.forName(this.coreBusinessClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        R coreBusiness = BeanLocator.findBean(targetClass);
        return coreBusiness;
    }

    public enum FailedLogRetryStatus{
        FAILED,
        SUCCESS,
        ;
    }

    public enum FailedLogValidStatus{
        VALID,
        INVALID,

        ;
    }
}
