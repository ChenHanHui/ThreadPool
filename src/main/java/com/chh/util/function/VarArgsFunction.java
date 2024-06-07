package com.chh.util.function;

/**
 * 定义一个异步函数接口，该接口应该包含一个名为apply的方法，该方法接受可变数量的参数，并返回一个泛型类型的值。
 */
@FunctionalInterface
public interface VarArgsFunction<R> {
    R apply(Object... args);
}
