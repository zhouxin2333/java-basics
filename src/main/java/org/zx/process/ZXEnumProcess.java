package org.zx.process;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author zhouxin
 * @since 2019/9/18
 */
public interface ZXEnumProcess<T extends Enum, R, Q> extends InitializingBean {

    T enumType();

    Q process(R data);

    @Override
    default void afterPropertiesSet() {
        ZXEnumProcessContainer.register(this);
    }
}
