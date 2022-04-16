package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.stereotype.Component;

@Component
public class ShutdownService {

	private final IntegrationMBeanExporter integrationMBeanExporter;

	public ShutdownService(IntegrationMBeanExporter integrationMBeanExporter) {
		this.integrationMBeanExporter = integrationMBeanExporter;
	}

	@ServiceActivator
	public void stop() {
		integrationMBeanExporter.stopActiveComponents(TimeUnit.MINUTES.toMillis(10));
	}
}
