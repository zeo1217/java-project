package com.qf.shopcar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
@EnableEurekaClient
@MapperScan(basePackages = "com.qf.mapper")
@EnableFeignClients("com.qf.feign.api")
public class ShopCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCarApplication.class, args);
    }

}
