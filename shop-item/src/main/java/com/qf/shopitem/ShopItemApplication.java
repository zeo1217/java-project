package com.qf.shopitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //忽略数据源
@ComponentScan(basePackages = "com.qf")
@EnableEurekaClient
public class ShopItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopItemApplication.class, args);
    }

}
