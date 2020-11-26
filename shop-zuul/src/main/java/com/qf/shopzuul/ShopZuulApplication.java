package com.qf.shopzuul;

import com.sun.webkit.BackForwardList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableZuulProxy
@ComponentScan(basePackages = "com.qf")
public class ShopZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopZuulApplication.class, args);
    }

}
