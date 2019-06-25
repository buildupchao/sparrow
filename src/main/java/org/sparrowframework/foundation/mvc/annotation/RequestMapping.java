package org.sparrowframework.foundation.mvc.annotation;

import org.sparrowframework.foundation.constant.Constants;

import java.lang.annotation.*;

/**
 * @author buildupchao
 * @date 2019-06-24 23:01
 * @since JDK 1.8
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value() default "";

    Constants.MethodType method() default Constants.MethodType.GET;
}
