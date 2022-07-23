package com.example.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * フォロワーデータソースのQualifier。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("follower")
public @interface FollowerDataSource {
}
