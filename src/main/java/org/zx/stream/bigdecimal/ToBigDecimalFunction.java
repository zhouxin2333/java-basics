package org.zx.stream.bigdecimal;

import java.math.BigDecimal;

/**
 * @author zhouxin
 * @since 2018/2/6
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);

    static ToBigDecimalFunction<BigDecimal> identity() {
        return t -> t;
    }
}
