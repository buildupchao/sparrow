package org.sparrowframework.foundation.core.impl;

import java.lang.annotation.Annotation;
import java.util.List;

import org.sparrowframework.foundation.core.ClassScanner;
import org.sparrowframework.foundation.core.impl.support.AnnotationClassScannerSupport;
import org.sparrowframework.foundation.core.impl.support.ClassScannerSupport;
import org.sparrowframework.foundation.core.impl.support.SuperClassScannerSupport;

/**
 * @author buildupchao
 * @date 2019/06/19 18:05
 * @since JDK 1.8
 */
public class DefaultClassScanner implements ClassScanner {

	/*
	 * @see org.sparrowframework.foundation.core.ClassScanner#getClassList(java.lang.String)
	 */
	@Override
	public List<Class<?>> getClassList(String packageName) {
		return new ClassScannerSupport(packageName) {
			@Override
			public boolean checkIfAddClass(Class<?> cls) {
				String className = cls.getName();
				String holdPackageName = className.substring(0, className.lastIndexOf("."));
				return holdPackageName.startsWith(packageName);
			}
		}.getClassList();
	}

	/*
	 * @see org.sparrowframework.foundation.core.ClassScanner#getClassListByAnnotation(java.lang.String, java.lang.Class)
	 */
	@Override
	public List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
		return new AnnotationClassScannerSupport(packageName, annotationClass) {
			@Override
			public boolean checkIfAddClass(Class<?> cls) {
				return cls.isAnnotationPresent(annotationClass);
			}
		}.getClassList();
	}

	/* 
	 * @see org.sparrowframework.foundation.core.ClassScanner#getClassListByParentClass(java.lang.String, java.lang.Class)
	 */
	@Override
	public List<Class<?>> getClassListBySuperClass(String packageName, Class<?> superClass) {
		return new SuperClassScannerSupport(packageName, superClass) {
			@Override
			public boolean checkIfAddClass(Class<?> cls) {
				return superClass.isAssignableFrom(cls) && !superClass.equals(cls);
			}
		}.getClassList();
	}

}
