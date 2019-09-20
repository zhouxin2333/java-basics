package org.zx.async;

import com.alibaba.fastjson.JSONObject;
import org.zx.constants.DatabaseConstants;
import org.zx.service.ZXService;
import org.zx.utils.BeanLocator;
import org.zx.utils.ExceptionFormatUtils;
import org.zx.utils.GenericUtils;
import org.zx.utils.StringUtils;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
public abstract class ZXAbstractAsyncTask<T, R extends ZXAsyncTaskCoreBusiness<T>, Q extends ZXFailedLogEntity<T, R>> implements ZXAsyncTask<T> {


    @Override
    public void asyncExceptionHandler(Throwable ex, T context) {
        Q failedLog = GenericUtils.getSuperTypeThirdGenericObj(this.getClass());

        String errorStack = ExceptionFormatUtils.stackTraceFormat(ex);
        failedLog.setErrorStack(errorStack);

        String contextClass = context.getClass().getTypeName();
        failedLog.setContextClass(contextClass);

        String contextValue = JSONObject.toJSONString(context);
        failedLog.setContextValue(contextValue);

        String coreBusinessClass = GenericUtils.getSuperTypeSecondGeneric(this.getClass()).getTypeName();
        failedLog.setCoreBusinessClass(coreBusinessClass);

        this.extraInfo(failedLog, ex, context);

        String serviceName = DatabaseConstants.ENTITY_CLASS_TO_SERVICE_NAME_FUN.apply(failedLog.getClass());
        ZXService service = BeanLocator.findBeanByName(StringUtils.toFirstLowerCase(serviceName));
        service.saveOrUpdate(failedLog);
    }

    /**
     * 需要补充额外信息，可以在这里补充
     * @param failedLog
     * @param ex
     * @param context
     */
    protected void extraInfo(Q failedLog, Throwable ex, T context){

    }
}
