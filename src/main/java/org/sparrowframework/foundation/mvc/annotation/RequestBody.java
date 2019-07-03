package org.sparrowframework.foundation.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author buildupchao
 * @date 2019/06/28 09:29
 * @since JDK 1.8
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBody {
	String name() default "";
	
	boolean required() default true;
}
