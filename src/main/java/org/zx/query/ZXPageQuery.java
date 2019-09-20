package org.zx.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxin
 * @since 2019/6/6
 */
@Getter
@Setter
@NoArgsConstructor
public class ZXPageQuery implements Serializable {

    private static final long serialVersionUID = -6651826520046881604L;

    @ApiModelProperty(value = "当前页索引")
    private Integer pageIndex = Integer.valueOf(1);

    @ApiModelProperty(value = "每页数据容量")
    private Integer pageSize = Integer.valueOf(10);

    @ApiModelProperty(value = "排序方式")
    private List<ZXPageOrder> orders = new ArrayList<>();

    public <T extends ZXPageQuery> void transfer(T original){
        this.setPageIndex(original.getPageIndex());
        this.setPageSize(original.getPageSize());
        this.setOrders(original.getOrders());
    }

    public ZXPageQuery sortByAsc(String property){
        ZXPageOrder order = new ZXPageOrder(property, ZXPageOrder.Direction.ASC);
        this.addOrder(order);
        return this;
    }

    public ZXPageQuery sortByDesc(String property){
        ZXPageOrder order = new ZXPageOrder(property, ZXPageOrder.Direction.DESC);
        this.addOrder(order);
        return this;
    }

    private void addOrder(ZXPageOrder order){
        this.orders.add(order);
    }
}
