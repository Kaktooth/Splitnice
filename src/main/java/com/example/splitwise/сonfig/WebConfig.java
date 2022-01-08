package com.example.splitwise.сonfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/sign-in").setViewName("sign-in");
        registry.addViewController("/sign-up").setViewName("sign-up");

        registry.addViewController("/admin-page").setViewName("admin-page");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addRedirectViewController("/", "/dashboard");
        registry.addViewController("/groups").setViewName("groups");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/groups/new-group").setViewName("add-group");
        registry.addViewController("/support").setViewName("contacts");

        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/access-denied-page").setViewName("access-denied-page");
    }
}