package com.example.demo.component.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;

@Retention(RetentionPolicy.RUNTIME)
//defaultプロファイル以外の場合に有効化される
@Profile("!default")
public @interface ComponentImpl {
}
