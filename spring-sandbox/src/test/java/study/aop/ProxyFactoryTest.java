package study.aop;

import static org.junit.jupiter.api.Assertions.*;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

class ProxyFactoryTest {

	@Test
	void test() throws Exception {

		final MethodInterceptor interceptor = inv -> {
			if (inv.getMethod().getName().equals("say")) {
				return "World";
			}
			return inv.proceed();
		};

		final ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new Hello());
		proxyFactory.addAdvice(interceptor);
		final Hello proxy = (Hello) proxyFactory.getProxy();
		assertEquals("World", proxy.say());
	}

	public static class Hello {
		public String say() {
			return "Hello";
		}
	}
}
