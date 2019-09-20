package org.zx.utils;

/**
 * Created by zhouxin on 2015/12/18.
 * 包装类接口，定义包装的行为
 * 1、包装原始数据
 * 2、获取原始数据
 */
public interface IWrapper<T> {

    /**
     * 包装原始数据
     * @param value
     */
    void setValue(T value);

    /**
     * 获取原始数据
     * @return
     */
    T getValue();
}
