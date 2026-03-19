package com.example.common.accesslog;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogCustomizer implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

    @Override
    public void customize(ConfigurableTomcatWebServerFactory factory) {
        factory.addEngineValves(new LogbackValve());
    }
}
