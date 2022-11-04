package study.validation.standard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Size;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class ValidationSample {

	public static void main(final String[] args) {
		SpringApplication.run(ValidationSample.class, args);
	}

	/*
	 * ModelAttributeMethodProcessorの処理中にバリデーションが行われる。
	 * 例外を投げるかメソッドパラメーターにErrorsを渡すかは
	 * isBindExceptionRequiredメソッドで決まる。
	 *
	 */
	@GetMapping("/1")
	String get1(@Validated final Sample sample, final Errors errors) {
		if (errors.hasErrors()) {
			return "ERROR";
		}
		return sample.foo;
	}

	static class Sample {
		private String foo;

		@Size(max = 3)
		public String getFoo() {
			return foo;
		}

		public void setFoo(final String foo) {
			this.foo = foo;
		}
	}
}
