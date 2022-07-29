package com.example.mock.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Object> {

	private static final MethodInterceptor interceptor = invocation -> {
		throw new UnsupportedOperationException(invocation.getMethod().toGenericString());
	};
	private final Class<?> beanClass;
	private final ProxyFactory proxyFactory;

	public MyFactoryBean(Class<?> beanClass) {
		this.beanClass = beanClass;
		this.proxyFactory = new ProxyFactory(beanClass, interceptor);
	}

	@Override
	public Object getObject() throws Exception {
		return proxyFactory.getProxy();
	}

	@Override
	public Class<?> getObjectType() {
		return beanClass;
	}
}
