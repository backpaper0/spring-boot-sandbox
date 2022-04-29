package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {

	private String username;
	private boolean locked;
	private LocalDate passwordExpiration;
	private int loginFailureCount;
	private LocalDate validityFrom;
	private LocalDate validityTo;
	private LocalDateTime lastLoggedin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public LocalDate getPasswordExpiration() {
		return passwordExpiration;
	}

	public void setPasswordExpiration(LocalDate passwordExpiration) {
		this.passwordExpiration = passwordExpiration;
	}

	public int getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public LocalDate getValidityFrom() {
		return validityFrom;
	}

	public void setValidityFrom(LocalDate validityFrom) {
		this.validityFrom = validityFrom;
	}

	public LocalDate getValidityTo() {
		return validityTo;
	}

	public void setValidityTo(LocalDate validityTo) {
		this.validityTo = validityTo;
	}

	public LocalDateTime getLastLoggedin() {
		return lastLoggedin;
	}

	public void setLastLoggedin(LocalDateTime lastLoggedin) {
		this.lastLoggedin = lastLoggedin;
	}
}
