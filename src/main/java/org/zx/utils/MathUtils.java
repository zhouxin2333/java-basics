package org.zx.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhouxin
 * @since 2017/5/8
 */
public class MathUtils {


    /**
     * 计算数字里面的最小公倍数
     * LCM ：least common multiple
     * @param datas
     * @return
     */
    public static int countLCM(List<Integer> datas){
        // 去重
        List<Integer> collect = datas.stream().sorted().distinct().collect(Collectors.toList());
        if (ListUtils.isSizeOne(collect)) return ListUtils.findFirst(datas);

        // 求出最大值
        int maxValue = ListUtils.findLast(collect);

        // 最小公倍数，一定能整除数据源中的最大值，且一定是数据源中最大值的某一个倍数值
        // 为什么一定要用最大值去循环，而不是最小值或者其他值，因为用最大值可以尽可能少的减少比较次数，
        // 因为其他值的倍数可能还是没有最大值大，比如2，7， 若用2的倍数循环，就要先比较2,4,6，这些值都比7小
        // 直接用7，只用比较7,14就可以了
        int result = IntStream.iterate(1, i -> i + 1)
                              .map(i -> maxValue * i)
                              .filter(i -> collect.stream().allMatch(data -> i % data == 0))
                              .findFirst().getAsInt();
        return result;
    }

    public static int countLCM(Integer ... datas) {
        return countLCM(Arrays.stream(datas).collect(Collectors.toList()));
    }
}
