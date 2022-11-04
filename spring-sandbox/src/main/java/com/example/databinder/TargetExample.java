package com.example.databinder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public class TargetExample {

	@NotNull
	private String field1;
	@Max(1000)
	private Integer field2;
	@Max(1000)
	private Long field3;
	private BigDecimal field4;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime field5;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate field6;
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime field7;
	private boolean field8;

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public Integer getField2() {
		return field2;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}

	public Long getField3() {
		return field3;
	}

	public void setField3(Long field3) {
		this.field3 = field3;
	}

	public BigDecimal getField4() {
		return field4;
	}

	public void setField4(BigDecimal field4) {
		this.field4 = field4;
	}

	public LocalDateTime getField5() {
		return field5;
	}

	public void setField5(LocalDateTime field5) {
		this.field5 = field5;
	}

	public LocalDate getField6() {
		return field6;
	}

	public void setField6(LocalDate field6) {
		this.field6 = field6;
	}

	public LocalTime getField7() {
		return field7;
	}

	public void setField7(LocalTime field7) {
		this.field7 = field7;
	}

	public boolean isField8() {
		return field8;
	}

	public void setField8(boolean field8) {
		this.field8 = field8;
	}
}
