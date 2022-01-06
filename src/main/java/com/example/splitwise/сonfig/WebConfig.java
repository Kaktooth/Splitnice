package com.example.splitwise.сonfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/page").setViewName("page");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/sign-in").setViewName("sign-in");
        registry.addViewController("/sign-out").setViewName("sign-out");
        registry.addViewController("/sign-up").setViewName("sign-up");
        registry.addViewController("/").setViewName("page");
    }
}