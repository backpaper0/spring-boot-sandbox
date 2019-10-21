package study.requestscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RequestScopeExample {
    public static void main(final String[] args) {
        SpringApplication.run(RequestScopeExample.class, args);
    }
}
