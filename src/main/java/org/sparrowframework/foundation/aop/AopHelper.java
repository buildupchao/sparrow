package org.sparrowframework.foundation.aop;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.aop.annotation.Aspect;
import org.sparrowframework.foundation.aop.proxy.AspectProxy;
import org.sparrowframework.foundation.aop.proxy.Proxy;
import org.sparrowframework.foundation.aop.proxy.ProxyManager;
import org.sparrowframework.foundation.core.ClassHelper;
import org.sparrowframework.foundation.ioc.BeanHelper;

import com.google.common.collect.Lists;

/**
 * @author buildupchao
 * @date 2019/06/25 18:30
 * @since JDK 1.8
 */
public final class AopHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
	
	static {
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				// construct a proxy for target class
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				// register proxy into container.
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception ex) {
			LOGGER.error("create aop proxy failure", ex);
			throw new RuntimeException(ex);
		}
	}
	
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
		addAspectProxy(proxyMap);
		// TODO other proxy
		return proxyMap;
	}

	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuperClass(AspectProxy.class);
		for (Class<?> proxyClass : proxyClassSet) {
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}

	private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
		Set<Class<?>> targetClassSet = new HashSet<>();
		Class<? extends Annotation> annotationClass = aspect.value();
		if (annotationClass != null && !annotationClass.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotationClass));
		}
		return targetClassSet;
	}
	
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(proxyClass).add(proxy);
				} else {
					targetMap.put(targetClass, Lists.newArrayList(proxy));
				}
			}
		}
		return targetMap;
	}
}
