package com.chh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnotherService {

    @Autowired
    private AsyncService asyncService;

    public void executeAsyncMethod() {
        asyncService.asyncMethod(); // 这将确保异步执行
    }

}
