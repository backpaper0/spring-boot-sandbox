package study.pojoparam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
	@GetMapping
	public String say(@RequestParam final Name name) {
		return "Hello, " + name.value + "!";
	}

	public static class Name {
		public final String value;

		public Name(final String value) {
			this.value = value;
			new Throwable().printStackTrace(System.out);
		}
	}
}
