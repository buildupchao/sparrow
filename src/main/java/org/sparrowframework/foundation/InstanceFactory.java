package org.sparrowframework.foundation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.core.ClassScanner;
import org.sparrowframework.foundation.core.ConfigHelper;
import org.sparrowframework.foundation.core.impl.DefaultClassScanner;
import org.sparrowframework.foundation.mvc.HandlerExceptionResolver;
import org.sparrowframework.foundation.mvc.HandlerInvoker;
import org.sparrowframework.foundation.mvc.HandlerMapping;
import org.sparrowframework.foundation.mvc.impl.DefaultHandlerExceptionResolver;
import org.sparrowframework.foundation.mvc.impl.DefaultHandlerInvoker;
import org.sparrowframework.foundation.mvc.impl.DefaultHandlerMapping;
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
	
	public static HandlerMapping getHandlerMapping() {
		return getInstance(Constants.HANDLER_MAPPING, DefaultHandlerMapping.class);
	}
	
	public static HandlerInvoker getHandlerInvoker() {
		return getInstance(Constants.HANDLER_INVOKER, DefaultHandlerInvoker.class);
	}
	
	public static HandlerExceptionResolver getHandlerExceptionResolver() {
		return getInstance(Constants.HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
		if (INSTANCE_CACHE.containsKey(cacheKey)) {
			return (T) INSTANCE_CACHE.get(cacheKey);
		}
		
		String implClassName = ConfigHelper.getString(cacheKey);
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
