package com.chh.manager.factory;

import com.chh.service.AsyncService;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 */
public class AsyncFactory {

    public static TimerTask execute(final String message) {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println(message);
                AsyncService.getInstance().asyncMethod();
            }
        };
    }

}
