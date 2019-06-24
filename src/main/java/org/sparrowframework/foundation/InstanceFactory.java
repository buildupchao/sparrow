package org.sparrowframework.foundation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.core.ClassScanner;
import org.sparrowframework.foundation.core.impl.DefaultClassScanner;
import org.sparrowframework.foundation.util.ObjectUtil;

/**
 * @author buildupchao
 * @date 2019/06/24 15:23
 * @since JDK 1.8
 */
public class InstanceFactory {

	private static final Map<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<>();
	
	public static ClassScanner getClassScanner() {
		return getInstance(Constants.CLASS_SCANNER, DefaultClassScanner.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
		if (INSTANCE_CACHE.containsKey(cacheKey)) {
			return (T) INSTANCE_CACHE.get(cacheKey);
		}
		
		String implClassName = "";
		if (StringUtils.isBlank(implClassName)) {
			implClassName = defaultImplClass.getName();
		}
		
		T instance = ObjectUtil.newInstance(implClassName);
		if (instance != null) {
			INSTANCE_CACHE.put(cacheKey, instance);
		}
		return instance;
	}
}
