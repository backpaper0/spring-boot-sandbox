package com.example.handlingexception.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
