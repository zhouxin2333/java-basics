package org.zx.stream.localdate;

import java.time.LocalDate;

/**
 * @author zhouxin
 * @since 2018/2/6
 */
@FunctionalInterface
public interface ToLocalDateFunction<T> {

    LocalDate applyAsLocalDate(T value);

    static ToLocalDateFunction<LocalDate> identity() {
        return t -> t;
    }
}
