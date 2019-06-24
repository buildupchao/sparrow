package org.sparrowframework.foundation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 * @date 2019/06/24 16:08
 * @since JDK 1.8
 */
public class ObjectUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtil.class);
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		T instance = null;
		try {
			Class<T> commandClass = (Class<T>) ClassUtil.loadClass(className);
			instance = commandClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			LOGGER.error("Create instance for className={} error!", ex);
			throw new RuntimeException(ex);
		}
		return instance;
	}
}
