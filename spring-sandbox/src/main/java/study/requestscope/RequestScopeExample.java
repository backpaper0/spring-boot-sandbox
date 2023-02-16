package study.requestscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestScopeExample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(RequestScopeExample.class, args);
	}
}
