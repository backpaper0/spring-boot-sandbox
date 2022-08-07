package com.example.numbering;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Numbering {

	@Autowired
	private NumberingMapper mapper;
	@Autowired
	private LocalDateProvider localDateProvider;
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuuMMdd");

	public String numberingA() {
		long value = mapper.nextval("numbering1");
		return String.format("%020d", value);
	}

	public String numberingB() {
		long value = mapper.nextval("numbering2");
		return String.format("%05d", value);
	}

	public String numberingC() {
		long value = mapper.nextval("numbering3");
		LocalDate date = localDateProvider.now();
		return String.format("%1$tY%1$tm%1$td%2$04d", date, value);
	}

	public String numberingD() {
		long value = mapper.nextval("numbering4");
		LocalDate date = localDateProvider.now();
		return date.format(dateTimeFormatter) + String.format("%04d", value);
	}

	public long numberingE() {
		return mapper.nextval("numbering5");
	}

	public int numberingF() {
		return (int) mapper.nextval("numbering6");
	}
}
