package org.sparrowframework.foundation.util;

/**
 * @author buildupchao
 * @date 2019/06/19 17:01
 * @since JDK 1.8
 */
public class ClassUtils {

	
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		return loadClass(className, true, getClassLoader());
	}
	
	public static Class<?> loadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
		return loadClass(className, true, classLoader);
	}
	
	public static Class<?> loadClass(String className, boolean initialize, ClassLoader classLoader) throws ClassNotFoundException {
		return Class.forName(className, initialize, classLoader);
	}
}
