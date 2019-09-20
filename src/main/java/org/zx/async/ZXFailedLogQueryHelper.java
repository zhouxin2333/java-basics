package org.zx.async;

import org.zx.constants.DatabaseConstants;
import org.zx.service.ZXDefaultService;

/**
 * @author zhouxin
 * @since 2019/9/20
 */
public class ZXFailedLogQueryHelper {

    private ZXDefaultService service;

    private ZXFailedLogQueryHelper() {
    }

    private ZXFailedLogQueryHelper(ZXDefaultService service) {
        this.service = service;
    }

    public static ZXFailedLogQueryHelper target(Class<? extends ZXFailedLogEntity> targetClass){
        ZXDefaultService service = DatabaseConstants.ENTITY_CLASS_TO_SERVICE_FUN.apply(targetClass);
        ZXFailedLogQueryHelper helper = new ZXFailedLogQueryHelper(service);
        return helper;
    }
}
