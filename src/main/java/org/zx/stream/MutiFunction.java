package org.zx.stream;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by zhouxin on 2015/12/30.
 * Represents a function that accepts three arguments and produces a result.
 */
public interface MutiFunction<T, U, R, Q> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param r the third function argument
     * @return the function result
     */
    Q apply(T t, U u, R r);

    default <V> MutiFunction<T, U, R, V> andThen(Function<? super Q, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u, R r) -> after.apply(apply(t, u, r));
    }
}
