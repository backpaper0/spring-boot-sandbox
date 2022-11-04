package study.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfig {

	@Bean
	public Example example() {
		return new Example("hello");
	}
}
