package org.sparrowframework.foundation.ioc;

import org.sparrowframework.foundation.core.ClassHelper;
import org.sparrowframework.foundation.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buildupchao
 * @date 2019/06/24 18:02
 * @since JDK 1.8
 */
public class BeanHelper {

	private static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object instance = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<?> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("cannot get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls, Object instance) {
        if (cls == null || instance == null) {
            return;
        }
        BEAN_MAP.put(cls, instance);
    }
}
