package com.example.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * リーダーデータソースのQualifier。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("leader")
public @interface LeaderDataSource {
}
