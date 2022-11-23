package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@SpringBootApplication
public class JpaStreamExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(JpaStreamExampleApplication.class, args);
	}

	@Component
	static class DataSourceWrapper implements BeanPostProcessor {

		@Override
		public Object postProcessAfterInitialization(final Object bean, final String beanName)
				throws BeansException {
			if (bean instanceof DataSource) {
				final ClassLoader loader = getClass().getClassLoader();
				return Wrapper.wrap(loader, DataSource.class, bean);
			}
			return bean;
		}
	}

	static class Wrapper implements InvocationHandler {

		private static final Method next = ReflectionUtils.findMethod(ResultSet.class, "next");

		private final ClassLoader loader;
		private final Object obj;

		public Wrapper(final ClassLoader loader, final Object obj) {
			this.loader = loader;
			this.obj = obj;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args)
				throws Throwable {

			final Object returnValue = method.invoke(obj, args);

			if (method.equals(next)) {
				final StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
				System.out.printf("next(%s#%s): %s%n", ste.getClassName(), ste.getMethodName(),
						returnValue);
			}

			if (returnValue instanceof Connection
					|| returnValue instanceof Statement
					|| returnValue instanceof ResultSet) {
				return wrap(loader, method.getReturnType(), returnValue);
			}

			return returnValue;
		}

		static Object wrap(final ClassLoader loader, final Class<?> c, final Object obj) {
			final Class<?>[] interfaces = { c };
			final Wrapper h = new Wrapper(loader, obj);
			return Proxy.newProxyInstance(loader, interfaces, h);
		}
	}
}
