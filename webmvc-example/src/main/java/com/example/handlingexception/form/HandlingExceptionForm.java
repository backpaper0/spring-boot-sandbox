package com.example.handlingexception.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class HandlingExceptionForm {

	@NotNull
	@Size(min = 1, max = 10)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
