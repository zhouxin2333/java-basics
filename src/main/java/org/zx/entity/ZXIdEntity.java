package org.zx.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhouxin
 * @since 2019/9/16
 */
@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class ZXIdEntity extends ZXBaseEntity {

    /**
     * 唯一编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
}
