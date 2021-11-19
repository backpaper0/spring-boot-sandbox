package com.example;

import java.io.IOException;
import java.time.Duration;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final WebProperties.Resources resources;

	public WebMvcConfig(WebProperties webProperties) {
		this.resources = webProperties.getResources();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/index.html");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/")
				.setCachePeriod(getSeconds(resources.getCache().getPeriod()))
				.setCacheControl(resources.getCache().getCachecontrol().toHttpCacheControl())
				.resourceChain(true)
				.addResolver(new IndexHtmlResourceResolver());
	}

	private static Integer getSeconds(Duration cachePeriod) {
		return (cachePeriod != null) ? (int) cachePeriod.getSeconds() : null;
	}

	private static class IndexHtmlResourceResolver extends PathResourceResolver {
		@Override
		protected Resource getResource(String resourcePath, Resource location) throws IOException {
			Resource resource = super.getResource(resourcePath, location);
			if (resource != null) {
				return resource;
			}
			return super.getResource("index.html", location);
		}
	}
}
