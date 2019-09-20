package org.zx.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter FULL_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据0时区的本地时间获取本时区的本地时间
     * @param utcLocalDateTime
     * @return
     */
    public static final ZonedDateTime getCurrentLocalTime(LocalDateTime utcLocalDateTime){
        ZonedDateTime zonedDateTime = utcLocalDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
        return zonedDateTime;
    }

    /**
     * 获取本地时间时刻，utc的时间
     * @param localDateTime
     * @return
     */
    public static final ZonedDateTime getCurrentUTCTime(LocalDateTime localDateTime, ZoneId localZone){
        ZonedDateTime zonedDateTime = localDateTime.atZone(localZone).withZoneSameInstant(ZoneOffset.UTC);
        return zonedDateTime;
    }

    public static final ZonedDateTime getCurrentUTCTime(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = DateUtils.getCurrentUTCTime(localDateTime, ZoneId.systemDefault());
        return zonedDateTime;
    }

    public static ZoneOffset getCurrentZoneOffset(){
        Clock clock = Clock.systemDefaultZone();
        final Instant now = clock.instant();  // called once
        ZoneOffset offset = clock.getZone().getRules().getOffset(now);
        return offset;
    }
}