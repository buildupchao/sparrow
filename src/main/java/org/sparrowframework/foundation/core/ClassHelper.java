package org.sparrowframework.foundation.core;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.sparrowframework.foundation.InstanceFactory;
import org.sparrowframework.foundation.annotation.Controller;
import org.sparrowframework.foundation.annotation.Service;
import org.sparrowframework.foundation.constant.Constants;

/**
 * @author buildupchao
 * @date 2019/06/24 18:35
 * @since JDK 1.8
 */
public final class ClassHelper {

	private static final Set<Class<?>> CLASS_SET;

	private static final ClassScanner CLASS_SCANNER;

	static {
		String basePackage = ConfigHelper.getString(Constants.APP_BASE_PACKAGE);
		CLASS_SCANNER = InstanceFactory.getClassScanner();
		CLASS_SET = CLASS_SCANNER.getClassSet(basePackage);
	}

	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}
	
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	public static Set<Class<?>> getClassSetBySuperClass(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	public static Set<Class<?>> getControllerClassSet() {
		return getClassSetByAnnotation(Controller.class);
	}
	
	public static Set<Class<?>> getServiceClassSet() {
		return getClassSetByAnnotation(Service.class);
	}
	
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
}
