package com.example;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
public class IdController implements ApplicationContextAware, InitializingBean {

	private String id;
	private ApplicationContext context;
	private final ConsulDiscoveryProperties properties;

	public IdController(ConsulDiscoveryProperties properties) {
		this.properties = properties;
	}

	@GetMapping
	public Object get() {
		return Map.of("id", id);
	}

	@Override
	public void afterPropertiesSet() {
		id = ConsulAutoRegistration.getInstanceId(properties, context);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.context = applicationContext;
	}
}
