package org.zx.stream.localdatetime;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * @author zhouxin
 * @since 2018/2/6
 */
@FunctionalInterface
public interface LocalDateTimeConsumer extends Consumer<LocalDateTime> {
}
