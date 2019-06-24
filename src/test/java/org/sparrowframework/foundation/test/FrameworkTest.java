package org.sparrowframework.foundation.test;

import org.sparrowframework.foundation.boot.SparrowStarter;
import org.sparrowframework.foundation.test.controller.HelloController;
import org.sparrowframework.foundation.ioc.BeanHelper;

import java.util.Map;

/**
 * @author buildupchao
 * @date 2019-06-24 20:51
 * @since JDK 1.8
 */
public class FrameworkTest {

    public static void main(String[] args) {
        SparrowStarter.run(FrameworkTest.class, args);

        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        HelloController o = (HelloController) beanMap.get(HelloController.class);
        o.hello("buildupchao");
    }
}
