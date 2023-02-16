package study.publicfield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class PublicFieldSample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(PublicFieldSample.class, args);
	}
}

@RestController
class SampleController {
	@PostMapping("sample")
	String post(final SampleForm form) {
		return form.toString();
	}
}

@ControllerAdvice
class SampleAdvice {
	@InitBinder
	void init(final WebDataBinder binder) {
		binder.initDirectFieldAccess();
	}
}

class SampleForm {
	public String foo;
	public Integer bar;
	public boolean baz;

	@Override
	public String toString() {
		return String.format("%s:%s:%s", foo, bar, baz);
	}
}
