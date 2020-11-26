package com.qf.shopgoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
@MapperScan(basePackages = "com.qf.mapper")
@EnableEurekaClient
@EnableFeignClients("com.qf.feign.api")
public class ShopGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopGoodsApplication.class, args);
    }

}
