package org.zx.stream.range;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

/**
 * 一个月的开始时间，结束时间
 * @author zhouxin
 * @since 2018/6/5
 */
public class LocalDateTimeRange {

    private LocalDateTime start;
    private LocalDateTime end;

    public LocalDateTimeRange(LocalDateTime someDay) {
        this.start = someDay.with(TemporalAdjusters.firstDayOfMonth());
        this.end = someDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDateTimeRange(Month month) {
        LocalDateTime monthDay = LocalDateTime.now().withMonth(month.getValue());
        this.start = monthDay.with(TemporalAdjusters.firstDayOfMonth());
        this.end = monthDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDateTimeRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
