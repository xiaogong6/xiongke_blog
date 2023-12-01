package com.core.annotation;

import java.lang.annotation.*;

/**
 * @author xiongke
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 每秒
     */
    int seconds();

    /**
     * 最大访问次数
     */
    int maxCount();
}
