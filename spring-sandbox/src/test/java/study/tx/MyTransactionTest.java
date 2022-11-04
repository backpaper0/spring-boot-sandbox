package study.tx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.IllegalTransactionStateException;

public class MyTransactionTest {

	@Test
	void method1() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(MyTransactionConfig.class);
			context.refresh();

			final Bar bar = context.getBean(Bar.class);
			bar.method1();
		}
	}

	@Test
	void method2() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(MyTransactionConfig.class);
			context.refresh();

			final Bar bar = context.getBean(Bar.class);
			assertThrows(IllegalTransactionStateException.class, () -> {
				bar.method2();
			});
		}
	}

	@Test
	void method3() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(MyTransactionConfig.class);
			context.refresh();

			final Bar bar = context.getBean(Bar.class);
			bar.method3();
		}
	}
}
