package study.requestbody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication
@RestController
public class RequestBodySample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(RequestBodySample.class, args);
	}

	/**
	 *
	 * @param foo
	 * @return
	 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
	 * @see org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
	 * @see org.springframework.http.converter.HttpMessageConverter
	 */
	@PostMapping("/")
	String post(@RequestBody final Foo foo) {
		return foo.toString();
	}

	static class Foo {

		private final String value;

		public Foo(@JsonProperty("value") final String value) {
			new Throwable().printStackTrace(System.out);
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}
