package com.asynctest.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HelloService {

    @Resource
    private SleepService sleepService;

    //callable中调用的方法
    public String task2 () throws Exception {
        System.out.println("异步:线程名: " +Thread.currentThread().getName());
        Thread.sleep(2000);
        return "this is callable";
    }

   //同步方法
    public String synchSayHello() {
        try {
            //sleepService.syncSleep();
            System.out.println("同步:线程名: " +Thread.currentThread().getName());
            Thread.sleep(3000);
            return "this is sync";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }

    //调用异步的asyncsleep方法
    public String asynchSayHello() {
        try {
            System.out.println("异步:线程名1: " +Thread.currentThread().getName());
            sleepService.asyncSleep();
            return "this is async";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
