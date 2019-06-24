package org.sparrowframework.foundation.core.impl.support;

import java.lang.annotation.Annotation;

/**
 * @author buildupchao
 * @date 2019/06/19 17:55
 * @since JDK 1.8
 */
public abstract class AnnotationClassScannerSupport extends ClassScannerSupport {

	protected final Class<? extends Annotation> annotationClass;
	
	protected AnnotationClassScannerSupport(String packageName, Class<? extends Annotation> annotationClass) {
		super(packageName);
		this.annotationClass = annotationClass;
	}
}
