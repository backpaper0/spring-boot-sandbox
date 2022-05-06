package com.example.types;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model4 {

	private int id;
	private long longValue;
	private BigDecimal bigDecimalValue;
	private String stringValue;
	private boolean boolValue1;
	private boolean boolValue2;
	private LocalDateTime dateTimeValue;
	private LocalDate dateValue;
}
