package org.zx.entity.converter;

import org.zx.utils.DateUtils;

import javax.persistence.AttributeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

/**
 * 数据库理存的是此刻的UTC时间
 * @author zhouxin
 * @since 2019/9/16
 */
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        return Optional.ofNullable(attribute)
                       .map(localDateTime -> Date.from(DateUtils.getCurrentUTCTime(localDateTime).toInstant()))
                       .orElse(null);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                       .map(date -> date.toInstant().atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                       .orElse(null);
    }
}
