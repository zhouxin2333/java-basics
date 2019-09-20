package org.zx.utils;

import org.zx.exception.ZXError;
import org.zx.exception.ZXException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by zhouxin on 2015/12/8.
 * Optional类的扩展，可以增加更多函数式编程的静态方法
 */
public class OptionalExt<T> extends AbstractWrapper<T>{

    /**
     * 空实现
     */
    private static final OptionalExt<?> EMPTY = new OptionalExt<>();

    /**
     * 私有化构造方法，只能通过of来构造该对象
     */
    private OptionalExt() {
        this.value = null;
    }

    /**
     * 构造一个空的OptionalExt对象
     * @param <T>
     * @return
     */
    public static<T> OptionalExt<T> empty() {
        OptionalExt<T> t = (OptionalExt<T>) EMPTY;
        return t;
    }

    /**
     * 私有化构造方法，只能通过of来构造该对象
     * @param value
     */
    private OptionalExt(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * of构造化方法，用于初始化OptionalExt对象
     * @param value
     * @param <T>
     * @return
     */
    public static <T> OptionalExt<T> of(T value) {
        return new OptionalExt<>(value);
    }

    /**
     * 初始化一个Otional对象，若为空，则默认返回一个empty对象
     * @param value
     * @param <T>
     * @return
     */
    public static <T> OptionalExt<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 获取包装的对象
     * @return
     */
    public T get() {
        if (this.getValue() == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.getValue();
    }

    /**
     * 若根据传入的条件predicate判断成立，则抛出一个IError错误
     * @param predicate
     * @param error
     */
    public void ifException(Predicate<T> predicate, ZXError error) {
        if (predicate.test(this.getValue())) throw new ZXException(error);
    }

    /**
     * 若根据传入的条件predicate判断成立，则进行consumer处理
     * @param predicate
     * @param consumer
     */
    public void ifPresent(Predicate<T> predicate, Consumer<? super T> consumer) {
        if (value != null && predicate.test(this.getValue()))
            consumer.accept(this.getValue());
    }
}
