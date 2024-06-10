package com.chh.util;

import com.chh.manager.AsyncManager;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class ReflectionAsyncUtil {

    /**
     * 异步执行一个对象的方法，自动处理参数传递。
     *
     * @param targetObject 目标对象
     * @param methodName 方法名称
     * @param args 方法参数
     * @return CompletableFuture，表示异步操作的结果
     */
    public static CompletableFuture<Object> executeAsync(Object targetObject, String methodName, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 获取所有参数的类类型，用于定位方法
                Class<?>[] parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }

                // 定位方法
                Method method = findMethod(targetObject.getClass(), methodName, parameterTypes);

                // 调用方法
                return method.invoke(targetObject, args);
            } catch (Exception e) {
                throw new RuntimeException("Failed to invoke method asynchronously", e);
            }
        }, AsyncManager.me().getThreadPool());
    }

    /**
     * 查找匹配的方法，考虑自动装箱和基本类型。
     *
     * @param clazz 目标类
     * @param methodName 方法名称
     * @param parameterTypes 参数类型数组
     * @return 匹配的Method对象
     * @throws NoSuchMethodException 如果没有找到匹配的方法
     */
    private static Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        try {
            // 尝试直接查找方法
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            // 如果直接查找失败，遍历所有方法，考虑自动装箱和基本类型的匹配
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(methodName) && isParameterTypesCompatible(method.getParameterTypes(), parameterTypes)) {
                    return method;
                }
            }
            // 如果没有找到匹配的方法，抛出异常
            throw new NoSuchMethodException("No suitable method found for " + methodName);
        }
    }

    /**
     * 检查参数类型是否兼容，考虑自动装箱和基本类型。
     *
     * @param methodParameterTypes 方法的参数类型
     * @param actualParameterTypes 实际的参数类型
     * @return 如果兼容返回true，否则返回false
     */
    private static boolean isParameterTypesCompatible(Class<?>[] methodParameterTypes, Class<?>[] actualParameterTypes) {
        if (methodParameterTypes.length != actualParameterTypes.length) {
            return false;
        }
        for (int i = 0; i < methodParameterTypes.length; i++) {
            if (!methodParameterTypes[i].isAssignableFrom(actualParameterTypes[i]) &&
                    !isPrimitiveAndWrapper(methodParameterTypes[i], actualParameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查一个是不是另一个的基本类型或包装类型。
     *
     * @param baseType 基本类型
     * @param wrapperType 包装类型
     * @return 如果匹配返回true，否则返回false
     */
    private static boolean isPrimitiveAndWrapper(Class<?> baseType, Class<?> wrapperType) {
        if (baseType.isPrimitive()) {
            return (baseType == int.class && wrapperType == Integer.class) ||
                    (baseType == long.class && wrapperType == Long.class) ||
                    (baseType == double.class && wrapperType == Double.class) ||
                    (baseType == float.class && wrapperType == Float.class) ||
                    (baseType == boolean.class && wrapperType == Boolean.class) ||
                    (baseType == char.class && wrapperType == Character.class) ||
                    (baseType == byte.class && wrapperType == Byte.class) ||
                    (baseType == short.class && wrapperType == Short.class);
        } else {
            return isPrimitiveAndWrapper(wrapperType, baseType);
        }
    }
}