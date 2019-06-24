package org.sparrowframework.foundation.boot;

import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.HelperLoader;
import org.sparrowframework.foundation.mvc.server.TomcatServer;

/**
 * @author buildupchao
 * @date 2019/06/19 16:19
 * @since JDK 1.8
 */
public class SparrowStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparrowStarter.class);

    public static void run(Class<?> cls, String[] args) {
        LOGGER.info("Hello sparrowframework!");

        TomcatServer server = new TomcatServer(args);
        try {
            server.startServer();

            HelperLoader.init();
            LOGGER.info("helper init successfully!");
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
