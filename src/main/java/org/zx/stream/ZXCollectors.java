package org.zx.stream;


import org.zx.stream.bigdecimal.BigDecimalStatistics;
import org.zx.stream.bigdecimal.ToBigDecimalFunction;
import org.zx.stream.localdate.LocalDateStatistics;
import org.zx.stream.localdate.ToLocalDateFunction;
import org.zx.stream.localdatetime.LocalDateTimeStatistics;
import org.zx.stream.localdatetime.ToLocalDateTimeFunction;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 定制Collectors
 *
 * @author zhouxin
 * @since 2018/2/6
 */
public class ZXCollectors {

    static final Set<Collector.Characteristics> CH_CONCURRENT_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_CONCURRENT_NOID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED));
    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_UNORDERED_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH));
    static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    public static <T> Collector<T, List<List<T>>, List<List<T>>> groupByNum(int num){
        return new GroupCollectorImpl(num);
    }


    /**
     * 统计LocalDateTime
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> Collector<T, ?, LocalDateTimeStatistics> summarizingLocalDateTime(ToLocalDateTimeFunction<? super T> mapper) {
        return new CollectorImpl<T, LocalDateTimeStatistics, LocalDateTimeStatistics>(
                LocalDateTimeStatistics::new,
                (r, t) -> r.accept(mapper.applyAsLocalDateTime(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    /**
     * 统计LocalDate
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> Collector<T, ?, LocalDateStatistics> summarizingLocalDate(ToLocalDateFunction<? super T> mapper) {
        return new CollectorImpl<T, LocalDateStatistics, LocalDateStatistics>(
                LocalDateStatistics::new,
                (r, t) -> r.accept(mapper.applyAsLocalDate(t)),
                (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    /**
     * 统计Bigdecimal
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> Collector<T, ?, BigDecimalStatistics> summarizingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<T, BigDecimalStatistics, BigDecimalStatistics>(
                            BigDecimalStatistics::new,
                            (r, t) -> r.accept(mapper.applyAsBigDecimal(t)),
                            (l, r) -> { l.combine(r); return l; }, CH_ID);
    }

    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Function<A,R> finisher,
                      Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }
}
