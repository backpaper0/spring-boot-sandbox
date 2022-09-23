package com.example;

import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
public class AccessLogCustomizer
		implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

	@Override
	public void customize(ConfigurableTomcatWebServerFactory factory) {
		LogbackValve logbackValve = new LogbackValve();
		factory.addEngineValves(logbackValve);
	}
}
