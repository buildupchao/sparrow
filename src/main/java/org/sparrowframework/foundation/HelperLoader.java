package org.sparrowframework.foundation;

import org.sparrowframework.foundation.aop.AopHelper;
import org.sparrowframework.foundation.ioc.BeanHelper;
import org.sparrowframework.foundation.ioc.IocHelper;
import org.sparrowframework.foundation.mvc.ControllerHelper;
import org.sparrowframework.foundation.util.ClassUtil;

/**
 * @author buildupchao
 * @date 2019-06-24 20:06
 * @since JDK 1.8
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
