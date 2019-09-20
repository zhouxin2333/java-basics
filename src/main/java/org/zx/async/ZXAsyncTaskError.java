package org.zx.async;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zx.exception.ZXError;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
@Getter
@AllArgsConstructor
public enum ZXAsyncTaskError implements ZXError {

    BASE_SPRING_ASYNC_NO_EXCEPTION_HANDLER_METHOD_FOUND("异步任务（%s）没有找到对应的异常处理方法"),
    BASE_SPRING_ASYNC_EXCEPTION_HANDLER_EXECUTE_ERROR("异步任务（%s）执行异常处理失败"),

    ;

    private String msg;
}
