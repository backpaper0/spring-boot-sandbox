package com.example.methodsecurity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@ComponentScan
public class ProtectedHelloConfig {
	//nop
}
