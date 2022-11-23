package study.requestscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RequestScopeExample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(RequestScopeExample.class, args);
	}
}
