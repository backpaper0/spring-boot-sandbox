package com.example;

import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JerseyApp extends ResourceConfig implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		final String[] resourceClassBeanNames = applicationContext
				.getBeanNamesForAnnotation(Path.class);
		for (final String resourceClassBeanName : resourceClassBeanNames) {
			final Class<?> resourceClass = applicationContext.getType(resourceClassBeanName);
			register(resourceClass);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
