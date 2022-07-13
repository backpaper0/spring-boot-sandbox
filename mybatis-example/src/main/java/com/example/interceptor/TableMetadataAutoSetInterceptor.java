package com.example.interceptor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * テーブルがメタデータのカラムを持つ場合、自動で設定するInterceptor。
 * 
 * MyBatisのInterceptorについては以下のURLを参照。
 * https://mybatis.org/mybatis-3/ja/configuration.html#plugins
 *
 */
@Component
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
})
public class TableMetadataAutoSetInterceptor implements Interceptor {

	private final Map<Class<?>, TableMetadataSetters> tableMetadataSettersCache = new ConcurrentHashMap<>();

	@Autowired
	private IdSupplier idSupplier;
	@Autowired
	private LocalDateTimeSupplier localDateTimeSupplier;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object model = args[1];

		Class<?> modelClass = model.getClass();
		TableMetadataSetters tms = tableMetadataSettersCache.computeIfAbsent(modelClass, TableMetadataSetters::create);
		SqlCommandType sqlCommandType = ms.getSqlCommandType();
		String by = idSupplier.get();
		LocalDateTime at = localDateTimeSupplier.get();
		tms.set(sqlCommandType, model, by, at);
		return invocation.proceed();
	}

	private static class TableMetadataSetters {

		Method setCreatedBy;
		Method setCreatedAt;
		Method setUpdatedBy;
		Method setUpdatedAt;

		static TableMetadataSetters create(Class<?> modelClass) {
			TableMetadataSetters tms = new TableMetadataSetters();
			tms.setCreatedBy = ReflectionUtils.findMethod(modelClass, "setCreatedBy", String.class);
			tms.setCreatedAt = ReflectionUtils.findMethod(modelClass, "setCreatedAt", LocalDateTime.class);
			tms.setUpdatedBy = ReflectionUtils.findMethod(modelClass, "setUpdatedBy", String.class);
			tms.setUpdatedAt = ReflectionUtils.findMethod(modelClass, "setUpdatedAt", LocalDateTime.class);
			return tms;
		}

		void set(SqlCommandType sqlCommandType, Object model, String by, LocalDateTime at) {
			switch (sqlCommandType) {
			case INSERT:
				setValue(setCreatedBy, model, by);
				setValue(setCreatedAt, model, at);
				setValue(setUpdatedBy, model, by);
				setValue(setUpdatedAt, model, at);
				break;
			case UPDATE:
				setValue(setUpdatedBy, model, by);
				setValue(setUpdatedAt, model, at);
				break;
			default:
				// 何もしない
				break;
			}
		}

		static void setValue(Method setter, Object model, Object value) {
			if (setter != null) {
				ReflectionUtils.invokeMethod(setter, model, value);
			}
		}
	}

	public interface IdSupplier {
		String get();
	}

	public interface LocalDateTimeSupplier {
		LocalDateTime get();
	}
}
