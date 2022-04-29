package com.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedinUser implements Serializable {

	private LocalDateTime lastLoggedin;

	public LocalDateTime getLastLoggedin() {
		return lastLoggedin;
	}

	public void setLastLoggedin(LocalDateTime lastLoggedin) {
		this.lastLoggedin = lastLoggedin;
	}
}
