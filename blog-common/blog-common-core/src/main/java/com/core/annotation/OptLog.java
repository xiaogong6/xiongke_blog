package com.core.annotation;

import java.lang.annotation.*;

/**
 * @author xiongke
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    String optType() default "";
}
