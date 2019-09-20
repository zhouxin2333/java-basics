package org.zx.async;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
public interface ZXAsyncTask<T> {

    void run(T context);

    void asyncExceptionHandler(Throwable ex, T context);
}
