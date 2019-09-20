package org.zx.utils;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by zhouxin on 2015/12/30.
 * BigDecimal的包装类
 */
public class BigDecimalExt extends AbstractWrapper<BigDecimal> {

    /**
     * 空实现
     */
    private static final BigDecimalExt EMPTY = new BigDecimalExt();

    /**
     * 私有化构造方法，只能通过of来构造该对象
     */
    private BigDecimalExt() {
        this.value = null;
    }

    /**
     * 构造一个空的OptionalExt对象
     * @return
     */
    public static BigDecimalExt empty() {
        BigDecimalExt t = (BigDecimalExt) EMPTY;
        return t;
    }

    /**
     * 私有化构造方法，只能通过of来构造该对象
     * @param value
     */
    private BigDecimalExt(BigDecimal value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * 私有化构造方法，只能通过of来构造该对象
     * @param value
     */
    private BigDecimalExt(String value) {
        this.value = Objects.requireNonNull(BigDecimal.valueOf(Double.parseDouble(value)));
    }

    /**
     * of构造化方法，用于初始化OptionalExt对象
     * @param value
     * @return
     */
    public static BigDecimalExt of(BigDecimal value) {
        return new BigDecimalExt(value);
    }

    /**
     * of构造化方法，用于初始化OptionalExt对象
     * @param value
     * @return
     */
    public static BigDecimalExt of(String value) {
        return new BigDecimalExt(value);
    }

    /**
     * 初始化一个Otional对象，若为空，则默认返回一个empty对象
     * @param value
     * @return
     */
    public static BigDecimalExt ofNullable(BigDecimal value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 初始化一个Otional对象，若为空，则默认返回一个empty对象
     * @param value
     * @return
     */
    public static BigDecimalExt ofNullable(String value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 获取包装的对象
     * @return
     */
    public BigDecimal get() {
        if (this.getValue() == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.getValue();
    }

    /**
     * 大于等于
     * @param integer
     * @return
     */
    public Boolean greatThan(Integer integer){
        BigDecimal other = new BigDecimal(integer);
        return this.greatThan(other);
    }

    /**
     * 大于等于
     * @param integer
     * @return
     */
    public Boolean lessThan(Integer integer){
        BigDecimal other = new BigDecimal(integer);
        return this.lessThan(other);
    }

    /**
     * 大于等于
     * @param other
     * @return
     */
    public Boolean greatThan(BigDecimal other){
        return this.value.compareTo(other) == 0 || this.value.compareTo(other) == 1;
    }

    /**
     * 大于等于
     * @param other
     * @return
     */
    public Boolean lessThan(BigDecimal other){
        return this.value.compareTo(other) == 0 || this.value.compareTo(other) == -1;
    }

    /**
     * 将BigDecimal变成整数(四舍五入)
     * @param bigDecimal
     * @return
     */
    public String bigDecimal2Integer(BigDecimal bigDecimal){
        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        return df.format(bigDecimal);
    }

    /**
     * 将BigDecimal变成整数(四舍五入)
     * @return
     */
    public String bigDecimal2Integer(){
        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        return df.format(this.value);
    }


    /**
     * 将BigDecimal变成两位小数(四舍五入)
     * @param bigDecimal
     * @return
     */
    public String bigDecimal2TwoDecimalPlaces(BigDecimal bigDecimal){
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(bigDecimal);
    }

    /**
     * 将BigDecimal变成两位小数(四舍五入)
     * @return
     */
    public String bigDecimal2TwoDecimalPlaces(){
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(this.value);
    }

    /**
     * 只能返回两位的 bigDecimal 数据
     * 返回的结果是：
     *        A.如果数据类似 10.00，则返回数据为 10 的字符串
     *        B.如果数据类似 0.01，则返回数据为 0 的字符串
     *        C.如果数据类似 12.34，则返回数据为 12.34 的字符串
     *        D.如果数据类似 12.3456，则返回数据为 12.35 的字符串
     *        E.如果数据类似 0.02，则返回的数据为 0.02 的字符串
     * @param bigDecimal
     * @return
     */
    public String toIntegerOrBigDecimal(BigDecimal bigDecimal){
        // 变成2位小数
        BigDecimal originBigDecimal = new BigDecimal(this.bigDecimal2TwoDecimalPlaces(bigDecimal));
        Double originDouble = originBigDecimal.doubleValue() * 100;
        Integer originInteger = originDouble.intValue();
        // 取小数部分
        Integer afterDecimalPoint = originInteger % 100;
        // 取整数部分
        Integer beforeDecimalPoint = originInteger / 100;
        // 如果小数部分为0到这里
        if (afterDecimalPoint == 0){
            return this.bigDecimal2Integer(bigDecimal);
        }
        // 如果小数部分为1 且 整数部分是 0 ，说明是0.01，进入这里
        if (afterDecimalPoint == 1 && beforeDecimalPoint == 0){
            return "0";
        }
        return originBigDecimal.toString();
    }

    /**
     * 有小数的时候取就保留小数，没有的时候就保留整数
     * @param bigDecimal
     * @return
     */
    public String toBigDecimalOrInteger(BigDecimal bigDecimal){
        // 变成2位小数
        BigDecimal originBigDecimal = new BigDecimal(this.bigDecimal2TwoDecimalPlaces(bigDecimal));
        Double originDouble = originBigDecimal.doubleValue() * 100;
        Integer originInteger = originDouble.intValue();
        // 取小数部分
        Integer afterDecimalPoint = originInteger % 100;
        if (afterDecimalPoint == 0){
            return this.bigDecimal2Integer(bigDecimal);
        }else {
            return originBigDecimal.toString();
        }
    }

    public Boolean isPresent(){
        return this.value != null;
    }

    /**
     * 去掉末尾的0
     * @return
     */
    public String trimZeroGetString(){
        return isPresent() ? this.value.stripTrailingZeros().toPlainString() : null;
    }
}
