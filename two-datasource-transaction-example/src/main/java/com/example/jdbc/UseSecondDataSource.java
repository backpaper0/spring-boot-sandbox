package com.example.jdbc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@UseDataSource(DataSources.SECOND)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseSecondDataSource {
}
