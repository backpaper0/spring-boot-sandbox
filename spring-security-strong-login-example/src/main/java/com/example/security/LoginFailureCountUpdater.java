package com.example.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConfigurationProperties(prefix = "app")
public class LoginFailureCountUpdater {

	private final JdbcTemplate jdbc;
	private int maxLoginFailureCount;

	public LoginFailureCountUpdater(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * ログイン成功時のイベントハンドラーでログイン失敗回数をリセットする。
	 * 
	 * @param event
	 */
	@EventListener(AuthenticationSuccessEvent.class)
	public void resetLoginFailure(AuthenticationSuccessEvent event) {
		String username = event.getAuthentication().getName();
		jdbc.update("update accounts set login_failure_count = 0 where username = ?", username);
	}

	/**
	 * ログイン失敗時のイベントハンドラーでログイン失敗回数をインクリメントする。
	 * ログイン失敗回数が規定値を超えるとアカウントをロックする。
	 * 
	 * @param event
	 */
	@EventListener(AuthenticationFailureBadCredentialsEvent.class)
	@Transactional
	public void incrementLoginFailure(AuthenticationFailureBadCredentialsEvent event) {

		String username = event.getAuthentication().getName();

		List<Integer> loginFailureCounts = jdbc.queryForList(
				"select login_failure_count from accounts where username = ? for update",
				Integer.class, username);

		if (loginFailureCounts.isEmpty()) {
			return;
		}

		int loginFailureCount = loginFailureCounts.get(0);

		if (loginFailureCount < maxLoginFailureCount) {
			jdbc.update("update accounts set login_failure_count = ? where username = ?", loginFailureCount + 1,
					username);
		} else {
			jdbc.update("update accounts set locked = true where username = ?", username);
		}
	}

	public void setMaxLoginFailureCount(int maxLoginFailureCount) {
		this.maxLoginFailureCount = maxLoginFailureCount;
	}
}
