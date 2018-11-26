package com.example.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解
 * @author Administrator
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
	String LogAction() default "";
	
	String LogContent() default "";
	
	int ModuleID() default 0;

}
