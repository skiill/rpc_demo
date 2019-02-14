package com.xsj.anno;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyService {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
