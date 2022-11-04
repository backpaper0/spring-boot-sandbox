package com.example.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.transaction.annotation.Propagation;

import com.example.jdbc.DataSources;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTransactional {

	DataSources value();

	Propagation propagation() default Propagation.REQUIRED;
}
