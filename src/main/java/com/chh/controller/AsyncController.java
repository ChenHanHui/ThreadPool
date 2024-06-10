package com.chh.controller;

import com.chh.manager.AsyncManager;
import com.chh.manager.factory.AsyncFactory;
import com.chh.service.AnotherService;
import com.chh.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AnotherService anotherService;

    @RequestMapping("/async")
    public void asyncMethod() {
        asyncService.asyncMethod();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod() 方法执行完毕");
    }

    @RequestMapping("/async2")
    public void asyncMethod2() {
        asyncService.asyncMethod2();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod2() 方法执行完毕");
    }

    @RequestMapping("/instanceMethod")
    public void instanceMethod() {
        AsyncService.getInstance().asyncMethod();
        // 继续处理其他业务逻辑
        System.out.println("instanceMethod() 方法执行完毕");
    }

    @RequestMapping("/asyncMethod")
    public void executeAsyncMethod() {
        anotherService.executeAsyncMethod();
        // 继续处理其他业务逻辑
        System.out.println("executeAsyncMethod() 方法执行完毕");
    }

    @RequestMapping("/asyncFactory")
    public void asyncFactoryMethod() {
        AsyncManager.me().execute(AsyncFactory.execute("Hello World"));
        // 继续处理其他业务逻辑
        System.out.println("asyncFactoryMethod() 方法执行完毕");
    }

    @RequestMapping("/async3")
    public void asyncMethod3() {
        asyncService.asyncMethod3();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod3() 方法执行完毕");
    }

    @RequestMapping("/async4")
    public void asyncMethod4() {
        asyncService.asyncMethod4();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod4() 方法执行完毕");
    }

    @RequestMapping("/async5")
    public void asyncMethod5() {
        asyncService.asyncMethod5();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod5() 方法执行完毕");
    }

    @RequestMapping("/async6")
    public void asyncMethod6() {
        asyncService.asyncMethod6();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod6() 方法执行完毕");
    }

    @RequestMapping("/async7")
    public void asyncMethod7() {
        asyncService.asyncMethod7();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod7() 方法执行完毕");
    }

    @RequestMapping("/async8")
    public void asyncMethod8() {
        asyncService.asyncMethod8();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod8() 方法执行完毕");
    }

    @RequestMapping("/async9")
    public void asyncMethod9() {
        asyncService.asyncMethod9();
        // 继续处理其他业务逻辑
        System.out.println("asyncMethod9() 方法执行完毕");
    }

}