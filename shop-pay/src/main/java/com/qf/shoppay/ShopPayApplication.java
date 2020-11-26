package com.qf.shoppay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@ComponentScan(basePackages = "com.qf")
@EnableFeignClients(basePackages = "com.qf.feign.api")
public class ShopPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPayApplication.class, args);
    }

}
