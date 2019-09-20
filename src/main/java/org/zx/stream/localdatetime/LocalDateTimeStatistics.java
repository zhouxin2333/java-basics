package org.zx.stream.localdatetime;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * TemporalAccessor的统计
 * @author zhouxin
 * @since 2018/3/9
 */
public class LocalDateTimeStatistics implements LocalDateTimeConsumer {

    private long count;
    private LocalDateTime min;
    private LocalDateTime max;

    public LocalDateTimeStatistics() {
    }

    @Override
    public void accept(LocalDateTime localDate) {
        ++count;
        min = min == null ? localDate : this.min(min, localDate);
        max = max == null ? localDate : this.max(max, localDate);
    }

    public void combine(LocalDateTimeStatistics other) {
        count += other.count;
        min = min == null ? other.min : this.min(min, other.min);
        max = max == null ? other.max : this.max(max, other.max);
    }

    public long getCount() {
        return count;
    }

    public Optional<LocalDateTime> getMin() {
        return Optional.ofNullable(min);
    }

    public Optional<LocalDateTime> getMax() {
        return Optional.ofNullable(max);
    }

    private LocalDateTime min(LocalDateTime localDate1, LocalDateTime localDate2){
        return localDate1.isBefore(localDate2) ? localDate1 : localDate2;
    }

    private LocalDateTime max(LocalDateTime localDate1, LocalDateTime localDate2){
        return localDate1.isAfter(localDate2) ? localDate1 : localDate2;
    }

    @Override
    /**
     * {@inheritDoc}
     *
     * Returns a non-empty string representation of this object suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     */
    public String toString() {
        return String.format(
                "%s{count=%d, min=%s, max=%s}",
                this.getClass().getSimpleName(),
                getCount(),
                getMin().map(LocalDateTime::toString).orElse(""),
                getMax().map(LocalDateTime::toString).orElse(""));
    }
}
