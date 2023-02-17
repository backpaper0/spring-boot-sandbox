package com.example.web.hierarchicalroles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration(proxyBeanMethods = false)
public class RoleHierarchyConfig {

	@Bean
	RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_A > ROLE_B > ROLE_C");
		return roleHierarchy;
	}
}
