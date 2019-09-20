package org.zx.stream;

import org.zx.utils.ListUtils;

import java.util.List;

/**
 * 最大最小Vo对象
 *
 * @author zhouxin
 * @since 2017/6/22
 */
public class MaxMinVo<T> {
    private T min;
    private T max;

    /**
     * 默认datas是按照升序排列的
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> MaxMinVo<T> ofList(List<T> datas){
        return new MaxMinVo<>(ListUtils.findFirst(datas), ListUtils.findLast(datas));
    }

    private MaxMinVo(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
