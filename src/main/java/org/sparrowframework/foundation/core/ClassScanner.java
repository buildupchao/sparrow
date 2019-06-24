package org.sparrowframework.foundation.core;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author buildupchao
 * @date 2019/06/19 16:26
 * @since JDK 1.8
 */
public interface ClassScanner {

	/**
	 * Get all class by package.
	 * @param packageName
	 * @return
	 */
	Set<Class<?>> getClassSet(String packageName);
	
	/**
	 * Get all class with Annotation under the package.
	 * @param packageName
	 * @param annotationClass
	 * @return
	 */
	Set<Class<?>> getClassSetByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

	/**
	 * Get all class these extends some class under the package.
	 * @param packageName
	 * @param superClass
	 * @return
	 */
	Set<Class<?>> getClassSetBySuperClass(String packageName, Class<?> superClass);
}
