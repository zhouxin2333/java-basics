package org.zx.async;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
public interface ZXAsyncTaskCoreBusiness<T> {

    void doCore(T context);

    /**
     * 重试的合法性校验
     * @param context
     * @return
     */
    boolean canRetry(T context);
}
