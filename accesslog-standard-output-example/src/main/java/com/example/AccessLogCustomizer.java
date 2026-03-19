package com.example;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogCustomizer implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

    @Override
    public void customize(ConfigurableTomcatWebServerFactory factory) {
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setAsyncSupported(true);
        factory.addEngineValves(logbackValve);
    }
}
