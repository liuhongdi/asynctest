package com.asynctest.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SleepService {

    //异步方法
    @Async
    public void asyncSleep() throws Exception{
        System.out.println("异步:线程名2: " +Thread.currentThread().getName());
        Thread.sleep(3000);
    }

}
