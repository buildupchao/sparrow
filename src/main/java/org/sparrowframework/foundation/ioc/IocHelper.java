package org.sparrowframework.foundation.ioc;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.sparrowframework.foundation.annotation.Autowired;
import org.sparrowframework.foundation.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author buildupchao
 * @date 2019-06-24 19:53
 * @since JDK 1.8
 */
public class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtils.isEmpty(fields)) {
                    continue;
                }
                for (Field beanField : fields) {
                    if (beanField.isAnnotationPresent(Autowired.class)) {
                        Class<?> beanFieldClass = beanField.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if (beanFieldInstance != null) {
                            ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }
}
