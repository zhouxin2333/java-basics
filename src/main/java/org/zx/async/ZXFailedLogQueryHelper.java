package org.zx.async;

import org.zx.constants.DatabaseConstants;
import org.zx.service.ZXDefaultService;
import org.zx.utils.BeanLocator;
import org.zx.utils.ExceptionFormatUtils;
import org.zx.utils.GenericUtils;

import javax.persistence.Table;
import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/9/20
 */
public class ZXFailedLogQueryHelper {


    private Class<? extends ZXFailedLogEntity> targetClass;
    private ZXDefaultService service;
    private Table table;

    private static final String NOT_FOUND_BY_ID = "在%s.%s中没有找到对应的失败记录，请核对失败记录id（%s）";
    private static final String RETRY_RECORD_HAS_BEEN_SUCCESS = "失败记录已经重试成功过了，请勿重复操作";
    private static final String VALID_STATUS_IS_INVALID = "失败记录状态非法，重试失败，请根据业务场景核对关键id（%s）的情况";
    private static final String CAN_NOT_RETRY = "失败记录无法重试";
    private static final String RETRY_FAILED = "重试失败，请检查失败日志";


    private ZXFailedLogQueryHelper() {
    }

    public static ZXFailedLogQueryHelper target(Class<? extends ZXFailedLogEntity> targetClass){
        ZXDefaultService service = DatabaseConstants.ENTITY_CLASS_TO_SERVICE_FUN.apply(targetClass);
        ZXFailedLogQueryHelper helper = new ZXFailedLogQueryHelper();
        helper.service = service;
        helper.targetClass = targetClass;
        helper.table = targetClass.getAnnotation(Table.class);
        return helper;
    }

    public <T extends ZXFailedLogEntity, R extends ZXAsyncTaskCoreBusiness> String retry(Long failedLogId){

        Optional<T> failedLogEntityOptional = this.service.findById(failedLogId);
        if (!failedLogEntityOptional.isPresent()) {
            return String.format(NOT_FOUND_BY_ID, this.table.schema(), this.table.name(), failedLogId);
        }

        T failedLog = failedLogEntityOptional.get();
        if (failedLog.getRetryStatus().equals(ZXFailedLogEntity.FailedLogRetryStatus.SUCCESS)){
            return RETRY_RECORD_HAS_BEEN_SUCCESS;
        }

        if (failedLog.getValidStatus().equals(ZXFailedLogEntity.FailedLogValidStatus.INVALID)){
            String retrySuccessKeyId = failedLog.getRetrySuccessKeyId();
            return String.format(VALID_STATUS_IS_INVALID, retrySuccessKeyId);
        }

        Class<R> coreBusiness = GenericUtils.getSuperTypeSecondGeneric(this.targetClass);
        R bean = BeanLocator.findBean(coreBusiness);

        Object context = failedLog.getContext();
        if (!bean.canRetry(context)) {
            failedLog.setValidStatus(ZXFailedLogEntity.FailedLogValidStatus.INVALID);
            service.saveOrUpdate(failedLog);
            return CAN_NOT_RETRY;
        }
        try {
            String retrySuccessKeyId = bean.doCore(context);
            failedLog.setRetrySuccessKeyId(retrySuccessKeyId);
            failedLog.setRetryStatus(ZXFailedLogEntity.FailedLogRetryStatus.SUCCESS);
            service.saveOrUpdate(failedLog);
            return "success";
        }catch (Exception e){
            failedLog.setRetryTimes(failedLog.getRetryTimes() + 1);
            String errorStack = ExceptionFormatUtils.stackTraceFormat(e);
            failedLog.setErrorStack(errorStack);
            service.saveOrUpdate(failedLog);
            return RETRY_FAILED;
        }
    }
}
