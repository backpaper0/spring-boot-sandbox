package com.example.interceptor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @see <a href="https://mybatis.org/mybatis-3/ja/configuration.html#plugins">plugins</a>
 *
 */
@Component
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
})
public class MyInterceptor implements Interceptor {

	private final LocalDateTimeSupplier localDateTimeSupplier;

	public MyInterceptor(LocalDateTimeSupplier localDateTimeSupplier) {
		this.localDateTimeSupplier = localDateTimeSupplier;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object model = args[1];
		Class<?> modelClass = model.getClass();
		Method setCreatedAt = ReflectionUtils.findMethod(modelClass, "setCreatedAt", LocalDateTime.class);
		Method setUpdatedAt = ReflectionUtils.findMethod(modelClass, "setUpdatedAt", LocalDateTime.class);
		if (setCreatedAt != null && setUpdatedAt != null) {
			SqlCommandType sqlCommandType = ms.getSqlCommandType();
			LocalDateTime now = localDateTimeSupplier.now();
			if (sqlCommandType == SqlCommandType.INSERT) {
				ReflectionUtils.invokeMethod(setCreatedAt, model, now);
				ReflectionUtils.invokeMethod(setUpdatedAt, model, now);
			} else if (sqlCommandType == SqlCommandType.UPDATE) {
				ReflectionUtils.invokeMethod(setUpdatedAt, model, now);
			}
		}
		return invocation.proceed();
	}
}
