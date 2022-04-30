package com.example.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

public class PasswordForm {

	@NotNull
	@Size(min = 1)
	private String currentPassword;
	@NotNull
	@Size(min = 1)
	private String newPassword;
	@NotNull
	@Size(min = 1)
	private String confirmPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@AssertTrue(message = "{passwordConfirmationError}")
	public boolean getPasswordConfirmation() {
		if (!StringUtils.hasText(newPassword)) {
			return true;
		}
		if (!StringUtils.hasText(currentPassword)) {
			return true;
		}
		return newPassword.equals(confirmPassword);
	}
}
