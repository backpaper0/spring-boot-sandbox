package com.example.error;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultErrorViewResolverConfig {

    private final ApplicationContext applicationContext;

    private final Resources resources;

    DefaultErrorViewResolverConfig(ApplicationContext applicationContext, WebProperties webProperties) {
        this.applicationContext = applicationContext;
        this.resources = webProperties.getResources();
    }

    @Bean
    public DefaultErrorViewResolver conventionErrorViewResolver() {
        return new DefaultErrorViewResolver(this.applicationContext, this.resources);
    }

}
