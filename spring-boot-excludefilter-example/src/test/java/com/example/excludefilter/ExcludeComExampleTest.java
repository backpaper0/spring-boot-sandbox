package com.example.excludefilter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;

@Retention(RetentionPolicy.RUNTIME)
@TypeExcludeFilters(ExcludeComExampleTestFilter.class)
public @interface ExcludeComExampleTest {

}
