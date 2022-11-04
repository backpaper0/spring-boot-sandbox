package com.example.form;

import org.springframework.util.StringUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
		if (!StringUtils.hasText(newPassword) || !StringUtils.hasText(currentPassword)) {
			return true;
		}
		return newPassword.equals(confirmPassword);
	}
}
