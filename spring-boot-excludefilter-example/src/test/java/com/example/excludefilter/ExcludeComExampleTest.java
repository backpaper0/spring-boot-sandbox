package com.example.excludefilter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.context.filter.annotation.TypeExcludeFilters;

@Retention(RetentionPolicy.RUNTIME)
@TypeExcludeFilters(ExcludeComExampleTestFilter.class)
public @interface ExcludeComExampleTest {}
