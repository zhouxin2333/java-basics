package org.zx.stream.range;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by zhouxin on 2016/12/2.
 */
public class ValueRangeCollectors {

    public static Collector<Integer, List<ValueRange>, List<ValueRange>> toList(){
        return new ValueRangeCollector();
    }

    static class ValueRangeCollector implements Collector<Integer, List<ValueRange>, List<ValueRange>> {

        private AtomicInteger atomicInteger = new AtomicInteger(0);

        @Override
        public Supplier<List<ValueRange>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<ValueRange>, Integer> accumulator() {
            return (valueRanges, integer) -> {
                if (CollectionUtils.isEmpty(valueRanges)){
                    valueRanges.add(ValueRange.of(1, integer, atomicInteger.getAndIncrement()));
                }else {
                    ValueRange last = valueRanges.get(valueRanges.size() - 1);
                    valueRanges.add(ValueRange.of(last.getMax() + 1, last.getMax() + integer, atomicInteger.getAndIncrement()));
                }
            };
        }

        @Override
        public BinaryOperator<List<ValueRange>> combiner() {
            return (valueRanges, valueRanges2) -> {
                valueRanges.addAll(valueRanges2);
                return valueRanges;
            };
        }

        @Override
        public Function<List<ValueRange>, List<ValueRange>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
        }
    }
}
