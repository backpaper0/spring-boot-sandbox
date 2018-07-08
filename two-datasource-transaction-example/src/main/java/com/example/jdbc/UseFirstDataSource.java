package com.example.jdbc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@UseDataSource(DataSources.FIRST)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseFirstDataSource {
}
