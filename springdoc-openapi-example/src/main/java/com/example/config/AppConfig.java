package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class AppConfig {

	@Autowired(required = false)
	private ServerProperties serverProperties;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						// https://swagger.io/docs/specification/authentication/
						.addSecuritySchemes("API Key",
								new SecurityScheme().type(Type.APIKEY).name("X-API-KEY").in(In.HEADER))
						.addSecuritySchemes("Basic Auth",
								new SecurityScheme().type(Type.HTTP).scheme("basic"))
						.addSecuritySchemes("Bearer Token",
								new SecurityScheme().type(Type.HTTP).scheme("bearer")))

				.addSecurityItem(new SecurityRequirement()
						.addList("API Key")
						.addList("Basic Auth")
						.addList("Bearer Token"))

				.addServersItem(new Server().url("http://localhost:"
						+ (serverProperties != null && serverProperties.getPort() != null ? serverProperties.getPort()
								: 8080)))
				.addServersItem(new Server().url("http://example.com"));
	}
}
