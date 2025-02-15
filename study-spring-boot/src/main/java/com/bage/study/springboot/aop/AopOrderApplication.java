package com.bage.study.springboot.aop;

import com.bage.study.springboot.aop.order.HelloAopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopOrderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AopOrderApplication.class, args);
    }


    @Autowired
    HelloAopOrderService helloAopOrderService;


    @Override
    public void run(String... args) throws Exception {
        helloAopOrderService.hello("world");
        helloAopOrderService.hello2("world");
        helloAopOrderService.hello3("world");
    }

}