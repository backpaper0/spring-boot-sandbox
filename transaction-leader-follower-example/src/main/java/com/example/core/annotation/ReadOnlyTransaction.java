package com.example.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.transaction.annotation.Transactional;

/**
 * Read onlyなトランザクションを表すアノテーション。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(readOnly = true)
public @interface ReadOnlyTransaction {
}
