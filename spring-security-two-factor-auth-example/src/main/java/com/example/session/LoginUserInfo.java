package com.example.session;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoginUserInfo implements Serializable {

	/**
	 * 二要素認証を通過したことを表すフラグ
	 */
	private boolean passedTwoFactorAuth;

	public boolean isPassedTwoFactorAuth() {
		return passedTwoFactorAuth;
	}

	public void passTwoFactorAuth() {
		this.passedTwoFactorAuth = true;
	}
}
