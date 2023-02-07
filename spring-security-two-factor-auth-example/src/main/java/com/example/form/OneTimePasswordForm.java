package com.example.form;

public class OneTimePasswordForm {

	private String oneTimePassword;
	private boolean trustMe;

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public boolean isTrustMe() {
		return trustMe;
	}

	public void setTrustMe(boolean trustMe) {
		this.trustMe = trustMe;
	}
}
