package com.chh.service;

import com.chh.util.AsyncUtil;
import com.chh.util.ReflectionAsyncUtil;
import com.chh.util.SpringUtils;
import com.chh.util.Threads;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 异步服务类，用于处理异步任务。
 */
@Service
@Slf4j
public class AsyncService {

    @Getter
    private static volatile AsyncService instance;

    @PostConstruct
    public void init() {
        instance = this;
    }

    @Async
    public void asyncMethod() {
        log.info("Start asyncMethod");
        // 执行三次，每次休眠一秒
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncMethod");
        throw new RuntimeException("Error occurred");
    }

    public void asyncMethod2() {
        // asyncMethod(); // 不会异步执行

        // 获取当前类的Spring代理
        AsyncService asyncService = SpringUtils.getBean(AsyncService.class);
        // 通过代理调用异步方法
        asyncService.asyncMethod();
    }

    // 无参无返回值异步方法示例
    public void asyncMethod3() {
        AsyncUtil.executeAsync(() -> {
            // 异步处理逻辑
            asyncExample();
            return null;
        }).thenAccept(result -> {
            // 异步处理完成后的操作
            log.info("Result: {}", result);
        });
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 一个参数有返回值异步方法示例
    public void asyncMethod4() {
        CompletableFuture<String> future = AsyncUtil.executeAsync(this::asyncExample, "Message");
        future.thenAccept(result -> log.info("Result: {}", result)); // 异步处理完成后的操作
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 两个参数有返回值异步方法示例
    public void asyncMethod5() {
        CompletableFuture<String> future = AsyncUtil.executeAsync(this::asyncExample, "Hello", 10);
        future.thenAccept(result -> log.info("Result: {}", result)); // 异步处理完成后的操作
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 三个参数有返回值异步方法示例
    public void asyncMethod6() {
        CompletableFuture<String> future = AsyncUtil.executeAsync(params -> {
            // 由...args传参知道第一个参数是String，第二个是Integer，第三个是Boolean
            String param1 = (String) params[0];
            Integer param2 = (Integer) params[1];
            Boolean param3 = (Boolean) params[2];
            // 处理逻辑
            return asyncExample(param1, param2, param3);
        }, "Message", 1, true);
        future.thenAccept(result -> log.info("Result: {}", result)); // 异步处理完成后的操作
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 四个参数无返回值异步方法示例
    public void asyncMethod7() {
        AsyncUtil.executeAsync(params -> {
            // 由...args传参知道第一个参数是String，第二个是Integer，第三个是Boolean, 第四个是String
            String param1 = (String) params[0];
            Integer param2 = (Integer) params[1];
            Boolean param3 = (Boolean) params[2];
            String param4 = (String) params[3];
            // 处理逻辑
            asyncExample(param1, param2, param3, param4);
            return null;
        }, "Message", 1, true, "Hello").thenAccept(result -> {
            // 异步处理完成后的操作
            log.info("Result: {}", result); // 返回 null
            log.info("End up doing other things...");
        });
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 参数越过下标（params[4]）的异常异步方法示例
    public void asyncMethod8() {
        AsyncUtil.executeAsync(params -> {
            // 由...args传参知道第一个参数是String，第二个是Integer，第三个是Boolean, 第四个是String
            String param1 = (String) params[0];
            Integer param2 = (Integer) params[1];
            Boolean param3 = (Boolean) params[2];
            String param4 = (String) params[4]; // 越过下标
            // 处理逻辑
            asyncExample(param1, param2, param3, param4);
            return null;
        }, "Message", 1, true, "Hello").thenAccept(result -> {
            // 异步处理完成后的操作
            log.info("Result: {}", result); // 返回 null
            log.info("End up doing other things...");
        }).exceptionally(e -> {
            // 异常处理
            log.error("Exception occurred: {}", e.getMessage());
            return null; // 返回默认值
        });
        // 做其他事情...
        log.info("Doing other things...");
    }

    // 使用反射工具类，自动处理参数传递
    public void asyncMethod9() {
        ReflectionAsyncUtil.executeAsync(this, "asyncExample", "Message", 1, true, "Hello", Map.of("param", "World"))
                .thenAccept(result -> {
                    // 异步处理完成后的操作
                    log.info("Result: {}", result);
                }).exceptionally(e -> {
                    // 异常处理
                    log.error("Exception occurred: {}", e.getMessage());
                    return null;
                });
    }

    private void asyncExample() {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
    }

    private String asyncExample(String message) {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
        return message;
    }

    private String asyncExample(String message, int num) {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
        log.info("Message: {}, Number: {}", message, num);
        return message + num;
    }

    private String asyncExample(String message, int num, boolean flag) {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
        log.info("Message: {}, Number: {}, Flag: {}", message, num, flag);
        return message + num + (flag ? "true" : "false");
    }

    private void asyncExample(String message, int num, boolean flag, String other) {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
        log.info("Message: {}, Number: {}, Flag: {}, Other: {}", message, num, flag, other);
    }

    public String asyncExample(String message, int num, boolean flag, String other, Map<String, Object> map) {
        log.info("Start asyncExample");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                Threads.sleep(1000);
            }
            log.info("for: {}", i);
        }
        log.info("End asyncExample");
        log.info("Message: {}, Number: {}, Flag: {}, Other: {}, Map: {}", message, num, flag, other, map);
        return message + num + (flag ? "true" : "false") + other + map;
    }

}
