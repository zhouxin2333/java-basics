package org.zx.stream.range;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2018/6/5
 */
public class LocalDateRanges {

    public static List<LocalDateRange> listByMonth(int count){
        LocalDate now = LocalDate.now();
        List<LocalDateRange> ranges = Stream.iterate(now, localDate -> localDate.plusMonths(1l))
                .limit(count)
                .map(LocalDateRange::new)
                .collect(Collectors.toList());
        return ranges;
    }

    public static List<LocalDateRange> listBy3Month(){
        return LocalDateRanges.listByMonth(3);
    }
}
