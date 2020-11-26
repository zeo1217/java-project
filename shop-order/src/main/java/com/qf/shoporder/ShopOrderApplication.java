package com.qf.shoporder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.qf")
@EnableEurekaClient
@EnableFeignClients("com.qf.feign.api")
@MapperScan(basePackages = "com.qf.mapper")
@EnableTransactionManagement
public class ShopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
    }

}
