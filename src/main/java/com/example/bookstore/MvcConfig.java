package com.example.bookstore;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/shopping").setViewName("shopping");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/order").setViewName("order");
        registry.addViewController("/orderCompleted").setViewName("orderCompleted");
    }

}

