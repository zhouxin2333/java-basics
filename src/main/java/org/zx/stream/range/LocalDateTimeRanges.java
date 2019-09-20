package org.zx.stream.range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2018/6/5
 */
public class LocalDateTimeRanges {

    public static List<LocalDateTimeRange> listFirstDayOfMonthByStartDate(int count, LocalDate startDate) {
        List<LocalDateTimeRange> ranges = new ArrayList<>();
        ranges.add(new LocalDateTimeRange(startDate.atStartOfDay(), startDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay()));
        for(int i = 0; i < count-1; i++) {
            startDate = startDate.plusMonths(1L);
            ranges.add(new LocalDateTimeRange(startDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(), startDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay()));
        }
        return ranges;
    }

    public static List<LocalDateTimeRange> listFirstDayOfMonthByMonth(int count){
        LocalDateTime firstDayOfMonth = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
        List<LocalDateTimeRange> ranges = Stream.iterate(firstDayOfMonth, localDate -> localDate.plusMonths(1l))
                .limit(count)
                .map(LocalDateTimeRange::new)
                .collect(Collectors.toList());
        return ranges;
    }

    public static List<LocalDateTimeRange> listByMonth(int count){
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTimeRange> ranges = Stream.iterate(now, localDate -> localDate.plusMonths(1l))
                .limit(count)
                .map(LocalDateTimeRange::new)
                .collect(Collectors.toList());
        return ranges;
    }

    public static List<LocalDateTimeRange> listBy3Month(){
        return LocalDateTimeRanges.listByMonth(3);
    }

    public static List<LocalDateTimeRange> listFirstDayOfMonthByMonth(){
        return LocalDateTimeRanges.listFirstDayOfMonthByMonth(3);
    }

    public static List<LocalDateTimeRange> listFirstDayOfMonthByStartDate(LocalDate startDate) {
        return LocalDateTimeRanges.listFirstDayOfMonthByStartDate(3, startDate);
    }
}
