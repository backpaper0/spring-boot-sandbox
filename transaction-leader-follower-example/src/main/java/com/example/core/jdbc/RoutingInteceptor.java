package com.example.core.jdbc;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import lombok.RequiredArgsConstructor;

/**
 * トランザクションの属性に応じて{@link RoutingDataSource}へ
 * データソースのタイプを設定するインターセプター。
 *
 */
@Component
@RequiredArgsConstructor
public class RoutingInteceptor implements MethodInterceptor {

	/**
	 * ルーティングデータソース
	 */
	private final RoutingDataSource routingDataSource;

	/**
	 * トランザクションの属性を取得するためのインターフェース
	 */
	private final TransactionAttributeSource tas;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// データソースのタイプが設定済みなら何もしない
		if (routingDataSource.dataSourceTypeIsSet()) {
			return invocation.proceed();
		}

		// トランザクションの属性を取得する
		Method method = invocation.getMethod();
		Class<?> targetClass = invocation.getThis().getClass();
		TransactionAttribute txAttr = tas.getTransactionAttribute(method, targetClass);

		try {
			// readOnlyの値に応じてデータソースのタイプを設定する
			if (txAttr.isReadOnly()) {
				routingDataSource.setDataSourceType(DataSourceType.FOLLOWER);
			} else {
				routingDataSource.setDataSourceType(DataSourceType.LEADER);
			}
			return invocation.proceed();
		} finally {
			routingDataSource.clearDataSourceType();
		}
	}
}
