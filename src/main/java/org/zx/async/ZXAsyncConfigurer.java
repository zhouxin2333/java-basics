package org.zx.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

/**
 * @author zhouxin
 * @since 2019/9/19
 */
public abstract class ZXAsyncConfigurer implements AsyncConfigurer {

    private String threadNamePrefix;
    private static final String THREAD_NAME_PREFIX_FORMAT = "%s-async-thread-";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int DEFAULT_AWAIT_TERMINATION_SECONDS = 60;


    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        this.threadNamePrefix = String.format(THREAD_NAME_PREFIX_FORMAT, applicationName);
    }

    protected String getThreadNamePrefix(){
        return this.threadNamePrefix;
    }

    protected int getPoolSize(){
        return DEFAULT_POOL_SIZE;
    }

    protected int getAwaitTerminationSeconds(){
        return DEFAULT_AWAIT_TERMINATION_SECONDS;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.taskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new ZXAsyncExceptionHandler();
    }

    public Executor taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(this.getPoolSize());
        executor.setThreadNamePrefix(this.getThreadNamePrefix());
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(this.getAwaitTerminationSeconds());
        executor.initialize();
        return executor;
    }
}
