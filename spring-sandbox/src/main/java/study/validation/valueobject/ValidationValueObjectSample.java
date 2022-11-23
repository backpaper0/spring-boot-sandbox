package study.validation.valueobject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class ValidationValueObjectSample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(ValidationValueObjectSample.class, args);
	}

	@PostMapping("/")
	String post(@Validated final SampleForm form, final Errors errors) {
		if (errors.hasErrors()) {
			/*
			 * 変換に失敗しても最後まで変換しようとするっぽい。
			 */
			System.out.println(errors.getFieldErrorCount());
			errors.getAllErrors().forEach(System.out::println);
			return "ERROR";
		}
		return form.toString();
	}

	@InitBinder
	void init(final WebDataBinder binder) {
		binder.initDirectFieldAccess();
	}

	static class SampleForm {
		public SimpleValueObject foo;
		public SimpleValueObject bar;
		public SimpleValueObject baz;

		@Override
		public String toString() {
			return String.format("%s%s%s", foo, bar, baz);
		}
	}
}
