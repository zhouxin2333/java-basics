package org.zx.stream.range;

/**
 * Created by zhouxin on 2016/12/2.
 */
public class ValueRange {

    private int min;

    private int max;

    private int index;

    public ValueRange(int min, int max, int index) {
        this.min = min;
        this.max = max;
        this.index = index;
    }

    public static ValueRange of(int min, int max, int index){
        return new ValueRange(min, max, index);
    }

    /**
     * 左右两边都是包含的
     * @param num
     * @return
     */
    public boolean isBetween(int num){
        return num >= this.min && num <= this.max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
