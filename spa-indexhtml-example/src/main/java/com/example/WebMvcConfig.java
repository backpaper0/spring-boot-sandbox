package com.example;

import java.io.IOException;
import java.time.Duration;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ResourceProperties resourceProperties;

    public WebMvcConfig(final ResourceProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index.html");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(getSeconds(resourceProperties.getCache().getPeriod()))
                .setCacheControl(
                        resourceProperties.getCache().getCachecontrol().toHttpCacheControl())
                .resourceChain(true)
                .addResolver(new IndexHtmlResourceResolver());
    }

    private static Integer getSeconds(final Duration cachePeriod) {
        return (cachePeriod != null) ? (int) cachePeriod.getSeconds() : null;
    }

    private static class IndexHtmlResourceResolver extends PathResourceResolver {
        @Override
        protected Resource getResource(final String resourcePath,
                final Resource location)
                throws IOException {
            final Resource resource = super.getResource(resourcePath, location);
            if (resource != null) {
                return resource;
            }
            return super.getResource("index.html", location);
        }
    }
}
