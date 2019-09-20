package org.zx.stream.localdate;

import java.time.LocalDate;
import java.util.Optional;

/**
 * TemporalAccessor的统计
 * @author zhouxin
 * @since 2018/3/9
 */
public class LocalDateStatistics implements LocalDateConsumer {

    private long count;
    private LocalDate min;
    private LocalDate max;

    public LocalDateStatistics() {
    }

    @Override
    public void accept(LocalDate localDate) {
        ++count;
        min = min == null ? localDate : this.min(min, localDate);
        max = max == null ? localDate : this.max(max, localDate);
    }

    public void combine(LocalDateStatistics other) {
        count += other.count;
        min = min == null ? other.min : this.min(min, other.min);
        max = max == null ? other.max : this.max(max, other.max);
    }

    public long getCount() {
        return count;
    }

    public Optional<LocalDate> getMin() {
        return Optional.ofNullable(min);
    }

    public Optional<LocalDate> getMax() {
        return Optional.ofNullable(max);
    }

    private LocalDate min(LocalDate localDate1, LocalDate localDate2){
        return localDate1.isBefore(localDate2) ? localDate1 : localDate2;
    }

    private LocalDate max(LocalDate localDate1, LocalDate localDate2){
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
                getMin().map(LocalDate::toString).orElse(""),
                getMax().map(LocalDate::toString).orElse(""));
    }
}
