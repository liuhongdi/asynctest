package com.asynctest.demo.controller;

import com.asynctest.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HelloService helloService;

    /**
     * 异步方法
     * @return
     */
    @RequestMapping("/callable")
    public Callable<String> deferredResult() throws Exception {
        System.out.println("控制层执行线程:" + Thread.currentThread().getName());
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("异步执行线程:" + Thread.currentThread().getName());
                String str = helloService.task2();
                Thread.sleep(1000);
                return str;
            }
        };
    }

    /**
     * 异步方法
     * @return
     */
    @RequestMapping("/async")
    public String getAsynHello(){
        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.asynchSayHello();
        System.out.println(s);
        long f = Instant.now().toEpochMilli();
        //return s+";time:"+(f-n);
        return s;
    }

    /**
     * 同步方法
     * * @return
     */
    @GetMapping("/sync")
    public String getSyncHello(){
        long n = Instant.now().toEpochMilli();
        //异步
        String s = helloService.synchSayHello();
        long f = Instant.now().toEpochMilli();
        return s;
    }
}
