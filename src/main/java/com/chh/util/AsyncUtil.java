package com.chh.util;

import com.chh.manager.AsyncManager;
import com.chh.util.function.VarArgsFunction;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 异步工具类
 */
public class AsyncUtil {

    private static final ThreadPoolTaskExecutor threadPool = AsyncManager.me().getThreadPool();

    /**
     * 无参数
     */
    public static <R> CompletableFuture<R> executeAsync(Supplier<R> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, threadPool);
    }

    /**
     * 单个参数
     */
    public static <T, R> CompletableFuture<R> executeAsync(Function<T, R> function, T param) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return function.apply(param);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, threadPool);
    }

    /**
     * 两个参数
     */
    public static <T, U, R> CompletableFuture<R> executeAsync(BiFunction<T, U, R> function, T param1, U param2) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return function.apply(param1, param2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, threadPool);
    }

    /**
     * 多个参数
     */
    public static <R> CompletableFuture<R> executeAsync(VarArgsFunction<R> function, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return function.apply(args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, threadPool);
    }

}