package org.zx.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZXPageOrder {

    @ApiModelProperty(value = "排序的属性名", required = true)
    private String property;

    @ApiModelProperty(value = "排序方向")
    private Direction direction = Direction.ASC;

    public enum Direction{
        ASC, DESC;
    }
}
