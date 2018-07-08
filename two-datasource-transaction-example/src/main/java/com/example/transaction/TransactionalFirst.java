package com.example.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Propagation;

import com.example.jdbc.DataSources;

@CustomTransactional(DataSources.FIRST)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionalFirst {

    @AliasFor(annotation = CustomTransactional.class)
    Propagation propagation() default Propagation.REQUIRED;
}
