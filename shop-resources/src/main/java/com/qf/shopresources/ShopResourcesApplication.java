package com.qf.shopresources;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;


@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
@Import(FdfsClientConfig.class) // 导入客户端配置
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ShopResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopResourcesApplication.class, args);
    }

}
