package org.sparrowframework.foundation.test;

import org.sparrowframework.foundation.boot.SparrowStarter;
import org.sparrowframework.foundation.test.controller.HelloController;
import org.sparrowframework.foundation.ioc.BeanHelper;

/**
 * @author buildupchao
 * @date 2019-06-24 20:51
 * @since JDK 1.8
 */
public class FrameworkTest {

    public static void main(String[] args) {
        SparrowStarter.run(FrameworkTest.class, args);

        HelloController o = (HelloController) BeanHelper.getBean(HelloController.class);
        o.hello("buildupchao");
    }
}
