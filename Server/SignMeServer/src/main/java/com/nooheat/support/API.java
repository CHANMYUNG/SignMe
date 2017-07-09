package com.nooheat.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by NooHeat on 16/06/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface API {
    Category category();
    String summary() default "";
    String params() default "";
    String requestBody() default "";
    String response() default "";
    int successCode() default 0;
    int failureCode() default 0;
    String etc() default "";
}
