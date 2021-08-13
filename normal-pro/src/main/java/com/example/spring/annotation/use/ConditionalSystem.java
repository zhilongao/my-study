package com.example.spring.annotation.use;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(SystemCondition.class)
public @interface ConditionalSystem {

    ConditionalSystem.Type type() default ConditionalSystem.Type.LINUX;

    /**
     * Available application types.
     */
    enum Type {

        /**
         * LINUX
         */
        LINUX,

        /**
         * WINDOWS
         */
        WINDOWS
    }
}
