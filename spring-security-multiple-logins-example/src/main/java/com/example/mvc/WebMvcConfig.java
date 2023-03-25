package com.example.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");

		registry.addViewController("/protected1/login").setViewName("protected1/login");
		registry.addViewController("/protected1/logout").setViewName("protected1/logout");
		registry.addViewController("/protected1").setViewName("protected1");

		registry.addViewController("/protected2/login").setViewName("protected2/login");
		registry.addViewController("/protected2/logout").setViewName("protected2/logout");
		registry.addViewController("/protected2").setViewName("protected2");
	}
}
