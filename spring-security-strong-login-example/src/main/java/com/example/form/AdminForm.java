package com.example.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public class AdminForm {

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validityFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate validityTo;

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
}
