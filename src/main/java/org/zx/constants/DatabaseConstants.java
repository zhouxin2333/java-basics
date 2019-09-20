package org.zx.constants;

import org.zx.service.ZXDefaultService;
import org.zx.utils.BeanLocator;
import org.zx.utils.StringUtils;

import java.util.function.Function;

/**
 * @author zhouxin
 * @since 2019/9/20
 */
public interface DatabaseConstants {

    String SERVICE_NAME_FORMAT = "%sServiceImpl";
    Function<Class, String> ENTITY_CLASS_TO_SERVICE_NAME_FUN = entityClass -> String.format(DatabaseConstants.SERVICE_NAME_FORMAT, entityClass.getSimpleName());
    Function<Class, ZXDefaultService> ENTITY_CLASS_TO_SERVICE_FUN = entityClass -> BeanLocator.findBeanByName(
            StringUtils.toFirstLowerCase(
                    DatabaseConstants.ENTITY_CLASS_TO_SERVICE_NAME_FUN.apply(entityClass)));
}
