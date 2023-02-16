package com.example.resttemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestTemplateBasicAuthTest {

	@Autowired
	RestTemplateBuilder builder;
	@LocalServerPort
	int port;

	@Test
	void testOk() {
		RestTemplate restTemplate = builder
				.basicAuthentication("user", "secretxxx")
				.build();
		String response = restTemplate.getForObject("http://localhost:" + port + "/test", String.class);
		assertEquals("Hello World", response);
	}

	@Test
	void testUnauthorized() {
		RestTemplate restTemplate = builder
				//				.basicAuthentication("user", "secret")
				.build();

		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
				() -> restTemplate.getForObject("http://localhost:" + port + "/test", String.class));
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
	}

	@TestConfiguration
	@Import(TestController.class)
	static class TestConfig {

		@Bean
		SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			return http
					.authorizeHttpRequests(c -> c.anyRequest().authenticated())
					.csrf(c -> c.disable())
					.httpBasic(Customizer.withDefaults())
					.build();
		}

		@Bean
		InMemoryUserDetailsManager userDetailsManager() {
			List<UserDetails> users = List.of(
					User
							.withUsername("user")
							.password("secretxxx")
							.passwordEncoder(passwordEncoder()::encode)
							.authorities("USER")
							.build());
			return new InMemoryUserDetailsManager(users);
		}

		@Bean
		Pbkdf2PasswordEncoder passwordEncoder() {
			return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		}
	}

	@TestComponent
	@RestController
	static class TestController {
		@GetMapping("/test")
		String get() {
			return "Hello World";
		}
	}
}
