package org.zx.utils;

/**
 * Created by zhouxin on 2015/12/18.
 * 抽象包装类，其中value作为原始元素，其他额外的属性在子类中指定
 */
public abstract class AbstractWrapper<T> implements IWrapper<T>{
    /**
     * 包装的原始数据
     */
    protected T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
