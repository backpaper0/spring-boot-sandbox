package study.requestscope;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class Foo implements CommandLineRunner {

	private final HttpServletRequest request;

	public Foo(final HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void run(final String... args) throws Exception {
		System.out.println(Thread.currentThread().getName());
		System.out.println(request.getClass());
		if (RequestContextHolder.getRequestAttributes() != null) {
			System.out.println(request.getAttribute("foo"));
		}
	}
}
