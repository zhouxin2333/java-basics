package org.zx.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.zx.exception.ZXException;
import org.zx.utils.BeanLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 一般采用@Async注解进行异步任务处理时，这里统一做一下约束
 * @author zhouxin
 * @since 2019/9/19
 */
public class ZXAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final String ASYNC_EXCEPTION_HANDLER_METHOD_NAME = "asyncExceptionHandler";

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        // 获取当前报错异步任务所在类
        Class<?> declaringClass = method.getDeclaringClass();
        // 找寻异常处理的方法
        try {
            Method asyncExceptionHandlerMethod = declaringClass.getSuperclass().getDeclaredMethod(ASYNC_EXCEPTION_HANDLER_METHOD_NAME, Throwable.class, Object.class);
            Object bean = BeanLocator.findBean(declaringClass);
            asyncExceptionHandlerMethod.invoke(bean, ex, params[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ZXException(ZXAsyncTaskError.BASE_SPRING_ASYNC_NO_EXCEPTION_HANDLER_METHOD_FOUND, declaringClass.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ZXException(ZXAsyncTaskError.BASE_SPRING_ASYNC_EXCEPTION_HANDLER_EXECUTE_ERROR, declaringClass.getName());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new ZXException(ZXAsyncTaskError.BASE_SPRING_ASYNC_EXCEPTION_HANDLER_EXECUTE_ERROR, declaringClass.getName());
        }
    }

//    @Override
//    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
//        // 获取当前报错异步任务所在类
//        Class<?> declaringClass = method.getDeclaringClass();
//
//        // 获取执行逻辑的方法参数
//        Class<?>[] asyncRunParameterTypes = method.getParameterTypes();
//        Class<?>[] asyncExceptionHandlerParameterTypes = Stream.concat(Stream.of(Throwable.class), Arrays.stream(asyncRunParameterTypes)).toArray(Class[]::new);
//
//        // 找寻异常处理的方法
//        try {
//            Method asyncExceptionHandlerMethod = declaringClass.getDeclaredMethod(ASYNC_EXCEPTION_HANDLER_METHOD_NAME, asyncExceptionHandlerParameterTypes);
//            Object bean = BeanLocator.findBean(declaringClass);
//            // 获取异常处理的方法参数
//            Object[] asyncExceptionHandlerParameter = Stream.concat(Stream.of(ex), Arrays.stream(params)).toArray(Object[]::new);
//            asyncExceptionHandlerMethod.invoke(bean, asyncExceptionHandlerParameter);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            throw new STException(BaseSpringAsyncError.BASE_SPRING_ASYNC_NO_EXCEPTION_HANDLER_METHOD_FOUND, declaringClass.getName());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            throw new STException(BaseSpringAsyncError.BASE_SPRING_ASYNC_EXCEPTION_HANDLER_EXECUTE_ERROR, declaringClass.getName());
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//            throw new STException(BaseSpringAsyncError.BASE_SPRING_ASYNC_EXCEPTION_HANDLER_EXECUTE_ERROR, declaringClass.getName());
//        }
//    }
}
