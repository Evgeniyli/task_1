package com.testframework.model.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BrowserSession {
    boolean createBrowserSession() default true;
}
