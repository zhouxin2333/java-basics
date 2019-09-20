package org.zx.stream.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * BigDecimal的统计
 *
 * @author zhouxin
 * @since 2018/2/6
 */
public class BigDecimalStatistics implements BigDecimalConsumer {

    private long count;
    private BigDecimal sum = BigDecimal.valueOf(0l);
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimalStatistics() {
    }

    @Override
    public void accept(BigDecimal bigDecimal) {
        ++count;
        sum = sum.add(bigDecimal);
        min = min == null ? bigDecimal : min.min(bigDecimal);
        max = max == null ? bigDecimal : max.max(bigDecimal);
    }

    public void combine(BigDecimalStatistics other) {
        count += other.count;
        sum = sum.add(other.sum);
        min = min == null ? other.min : min.min(other.min);
        max = max == null ? other.max : max.max(other.max);
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public Optional<BigDecimal> getMin() {
        return Optional.ofNullable(min);
    }

    public Optional<BigDecimal> getMax() {
        return Optional.ofNullable(max);
    }

    public final BigDecimal getAverage() {
        return this.getCount() > 0 ?  this.getSum().divide(BigDecimal.valueOf(this.getCount())) : BigDecimal.valueOf(0l);
    }

    public final BigDecimal getAverage(int scale, RoundingMode roundingMode) {
        return this.getCount() > 0 ?  this.getSum().divide(BigDecimal.valueOf(this.getCount()), scale, roundingMode) : BigDecimal.valueOf(0l);
    }

    public final BigDecimal getAverage(RoundingMode roundingMode) {
        return this.getAverage(0, roundingMode);
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
                "%s{count=%d, sum=%s, min=%s, average=%s, max=%s}",
                this.getClass().getSimpleName(),
                getCount(),
                getSum().toString(),
                getMin().map(BigDecimal::toString).orElse(""),
                getAverage().toString(),
                getMax().map(BigDecimal::toString).orElse(""));
    }
}
