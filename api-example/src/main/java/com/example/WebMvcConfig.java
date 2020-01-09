package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        //        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(API.class));
    }
}
