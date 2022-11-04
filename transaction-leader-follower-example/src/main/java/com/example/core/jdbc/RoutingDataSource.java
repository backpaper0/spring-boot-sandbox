package com.example.core.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ルーティングデータソース。
 * このデータソースは論理的なものであり、スレッドローカルに保持している値に応じて実際のデータソースを返す。
 *
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

	/**
	 * データソースのタイプを保持するスレッドローカル
	 */
	private final ThreadLocal<DataSourceType> dataSourceType = new ThreadLocal<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceType.get();
	}

	/**
	 * スレッドローカルへデータソースのタイプが設定されているかどうかを返す。
	 *
	 * @return データソースのタイプが設定されていればtrue
	 */
	public boolean dataSourceTypeIsSet() {
		return dataSourceType.get() != null;
	}

	/**
	 * スレッドローカルへデータソースのタイプを設定する。
	 *
	 * @param dataSourceType データソースのタイプ
	 */
	public void setDataSourceType(DataSourceType dataSourceType) {
		this.dataSourceType.set(dataSourceType);
	}

	/**
	 * スレッドローカルからデータソースのタイプをクリアする。
	 *
	 */
	public void clearDataSourceType() {
		dataSourceType.remove();
	}
}
