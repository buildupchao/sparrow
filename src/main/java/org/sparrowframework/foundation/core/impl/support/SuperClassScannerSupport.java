package org.sparrowframework.foundation.core.impl.support;

/**
 * @author buildupchao
 * @date 2019/06/19 18:00
 * @since JDK 1.8
 */
public abstract class SuperClassScannerSupport extends ClassScannerSupport {

	protected final Class<?> superClass;
	
	public SuperClassScannerSupport(String packageName, Class<?> superClass) {
		super(packageName);
		this.superClass = superClass;
	}
}
