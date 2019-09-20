package org.zx.stream;

/**
 * Created by simpletour_java on 2016/11/3.
 * TripleConsumer takes 3 arguments as input
 */
@FunctionalInterface
public interface TripleConsumer<T, U, V> {
    /**
     * Applies this function to the five arguments.
     *
     * @param t the 1st function argument
     * @param u the 2nd function argument
     * @param v the 3rd function argument
     */
    void accept(T t, U u, V v);
}
