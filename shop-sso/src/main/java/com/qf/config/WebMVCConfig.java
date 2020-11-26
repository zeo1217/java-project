package com.qf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("toRegisterPage").setViewName("register");
        registry.addViewController("toLoginPage").setViewName("login");
        registry.addViewController("toInputusername").setViewName("inputusername");
        registry.addViewController("toChangePasswordPage").setViewName("changePassword");
    }
}
