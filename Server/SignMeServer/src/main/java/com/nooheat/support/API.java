package com.nooheat.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by NooHeat on 16/06/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface API {
    Category category();
    String title();
    String summary() default "";
    String parameters() default "No parameter";
    int successCode() default 200;
    int failureCode() default 400;
}
