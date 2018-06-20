package com.example.globalmethodsecurityexample;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAuthorize {

    Roles[] value() default { Roles.FOO, Roles.BAR };
}
