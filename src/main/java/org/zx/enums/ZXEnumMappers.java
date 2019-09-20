package org.zx.enums;

import java.util.Optional;

/**
 * @author zhouxin
 * @since 2019/9/18
 */
public class ZXEnumMappers {

    public static <T extends ZXEnumMapper<R>, R> Optional<T> map(Class<T> enumClass, R targetEnum) {
        Optional<T> optional = ZXEnumWrapper.enumType(enumClass)
                                            .addPropertyFun(ZXEnumMapper::getMapperEnum)
                                            .addPropertyValue(targetEnum)
                                            .getEnum();
        return optional;
    }
}
