package com.chh.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 自定义异步异常处理类，实现AsyncUncaughtExceptionHandler接口
 */
@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        // 处理异步方法执行过程中发生的异常
        log.error("Async method execution failed. Method: {}, Exception: {}", method.getName(), throwable.getMessage());
        // 可以根据需要进行异常处理，比如发送邮件、记录日志等
        // ...
    }
}