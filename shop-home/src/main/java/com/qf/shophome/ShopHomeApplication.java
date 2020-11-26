package com.qf.shophome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShopHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopHomeApplication.class, args);
    }

}
