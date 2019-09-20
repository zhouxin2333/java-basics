package org.zx.stream.localdatetime;

import java.time.LocalDateTime;

/**
 * @author zhouxin
 * @since 2018/2/6
 */
@FunctionalInterface
public interface ToLocalDateTimeFunction<T> {

    LocalDateTime applyAsLocalDateTime(T value);

    static ToLocalDateTimeFunction<LocalDateTime> identity() {
        return t -> t;
    }
}
