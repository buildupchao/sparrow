package org.sparrowframework.foundation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 * @date 2019/06/19 17:01
 * @since JDK 1.8
 */
public class ClassUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
	
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static Class<?> loadClass(String className) {
		return loadClass(className, true);
	}
	
	public static Class<?> loadClass(String className, boolean initialize) {
		Class<?> cls = null;
		try {
			cls = Class.forName(className, initialize, getClassLoader());
		} catch (ClassNotFoundException ex) {
			LOGGER.error("load class failure", ex);
			throw new RuntimeException(ex);
		}
		return cls;
	}
}
