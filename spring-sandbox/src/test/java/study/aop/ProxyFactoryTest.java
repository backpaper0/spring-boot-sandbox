package study.aop;

import static org.junit.Assert.*;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryTest {

    @Test
    public void test() throws Exception {

        MethodInterceptor interceptor = inv -> {
            if (inv.getMethod().getName().equals("say")) {
                return "World";
            }
            return inv.proceed();
        };

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Hello());
        proxyFactory.addAdvice(interceptor);
        Hello proxy = (Hello) proxyFactory.getProxy();
        assertEquals("World", proxy.say());
    }

    public static class Hello {
        public String say() {
            return "Hello";
        }
    }
}
