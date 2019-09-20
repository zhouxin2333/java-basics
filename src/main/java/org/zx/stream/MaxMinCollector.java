package org.zx.stream;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 最大最小比较筛选器
 *
 * @author zhouxin
 * @since 2017/6/22
 */
public class MaxMinCollector {

    public static <T> MaxMinVo<T> select(List<T> datas){
        List<T> newDatas = datas.stream().sorted().collect(Collectors.toList());
        return MaxMinVo.ofList(newDatas);
    }
}
