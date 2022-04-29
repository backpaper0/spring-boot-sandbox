package com.example.security;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.example.model.LoggedinUser;

@Component
public class LastLoggedinUpdater {

	private final JdbcTemplate jdbc;
	private final LoggedinUser loggedinUser;

	public LastLoggedinUpdater(JdbcTemplate jdbc, LoggedinUser loggedinUser) {
		this.jdbc = jdbc;
		this.loggedinUser = loggedinUser;
	}

	/**
	 * ログイン成功時に最終ログイン日時を更新する。
	 * 
	 * @param event
	 */
	@EventListener(AuthenticationSuccessEvent.class)
	public void resetLoginFailure(AuthenticationSuccessEvent event) {
		String username = event.getAuthentication().getName();
		LocalDateTime lastLoggedin = LocalDateTime.now();
		loggedinUser.setLastLoggedin(lastLoggedin);
		jdbc.update("update accounts set last_loggedin = ? where username = ?", lastLoggedin, username);
	}
}
