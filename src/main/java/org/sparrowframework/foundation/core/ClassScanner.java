package org.sparrowframework.foundation.core;

import java.lang.annotation.Annotation;
import java.util.List;

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
	List<Class<?>> getClassList(String packageName);
	
	/**
	 * Get all class with Annotation under the package.
	 * @param packageName
	 * @param annotationClass
	 * @return
	 */
	List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

	/**
	 * Get all class these extends some class under the package.
	 * @param packageName
	 * @param parentClass
	 * @return
	 */
	List<Class<?>> getClassListBySuperClass(String packageName, Class<?> superClass);
}
