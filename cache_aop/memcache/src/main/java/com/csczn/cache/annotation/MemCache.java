package com.csczn.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for cache
 *
 * @date 05/06/2015
 * @author wangcheng
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MemCache {
    /**
     * Mode
     */
    enum Mode {
        CACHE,
        EVICT
    }

    /**
     * | separator of key
     * : separator of meaning
     * # SPEL expression
     * example: user:#user.name:#user.age|user:userList
     *
     * @return
     */
    String expression();

    Mode mode() default Mode.CACHE;

    int expire() default 3600;
}
