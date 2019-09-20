package org.zx.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.zx.entity.converter.LocalDateTimeAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author zhouxin
 * @since 2019/9/16
 */
@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class ZXBaseEntity implements Serializable, Cloneable {

    /**
     * 版本
     */
    @Version
    private Integer version;

    /**
     * 数据创建时间
     */
    @CreatedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "created_time",updatable = false)
    private LocalDateTime createdTime = LocalDateTime.now(ZoneOffset.systemDefault());

    /**
     * 数据更新时间
     */
    @LastModifiedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime = LocalDateTime.now(ZoneOffset.systemDefault());
}
