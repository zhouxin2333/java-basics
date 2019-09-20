package org.zx.stream.localdate;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * @author zhouxin
 * @since 2018/2/6
 */
@FunctionalInterface
public interface LocalDateConsumer extends Consumer<LocalDate> {
}
