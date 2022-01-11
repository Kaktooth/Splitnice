package com.example.splitwise.сonfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/sign-in").setViewName("sign-in");
        registry.addViewController("/sign-up").setViewName("sign-up");

        registry.addViewController("/admin-page").setViewName("admin-page");
        registry.addViewController("/dashboard/**").setViewName("dashboard");
        registry.addRedirectViewController("/", "/dashboard");
        registry.addViewController("/groups").setViewName("groups");

        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/groups/new-group").setViewName("add-group");
        registry.addViewController("/support").setViewName("contacts");
        registry.addViewController("/api/account");

        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/access-denied-page").setViewName("access-denied-page");
    }

    @Bean
    @Description("Thymeleaf JS Resolver")
    public ViewResolver javascriptViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
        resolver.setContentType("application/javascript");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[]{"*.js"});
        return resolver;
    }

    private ITemplateResolver javascriptTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("resources/static/js/");
        resolver.setCacheable(false);
        resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
        return resolver;
    }

    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
}