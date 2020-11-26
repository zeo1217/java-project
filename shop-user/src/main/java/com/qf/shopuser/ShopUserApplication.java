package com.qf.shopuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
@EnableEurekaClient
@MapperScan(basePackages = "com.qf.mapper")
public class ShopUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUserApplication.class, args);
    }

}
