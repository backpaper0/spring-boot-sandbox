package com.example.common.accesslog;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;

import ch.qos.logback.access.tomcat.LogbackValve;

public class AccessLogCustomizerTest {

	AccessLogCustomizer sut = new AccessLogCustomizer();

	@Test
	void LogbackValveを追加する() {
		ConfigurableTomcatWebServerFactory factory = mock(ConfigurableTomcatWebServerFactory.class);
		sut.customize(factory);
		verify(factory).addEngineValves(any(LogbackValve.class));
		verifyNoMoreInteractions(factory);
	}
}
