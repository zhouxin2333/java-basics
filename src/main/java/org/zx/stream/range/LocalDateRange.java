package org.zx.stream.range;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

/**
 * 一个月的开始时间，结束时间
 * @author zhouxin
 * @since 2018/6/5
 */
public class LocalDateRange {

    private LocalDate start;
    private LocalDate end;

    public LocalDateRange(LocalDate someDay) {
        this.start = someDay.with(TemporalAdjusters.firstDayOfMonth());
        this.end = someDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDateRange(Month month) {
        LocalDate monthDay = LocalDate.now().withMonth(month.getValue());
        this.start = monthDay.with(TemporalAdjusters.firstDayOfMonth());
        this.end = monthDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
