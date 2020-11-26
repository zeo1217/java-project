package com.qf.shopaddress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.mapper")
@EnableFeignClients("com.qf.feign.api")
public class ShopAddressApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAddressApplication.class, args);
    }

}
