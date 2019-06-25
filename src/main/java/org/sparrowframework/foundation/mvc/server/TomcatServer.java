package org.sparrowframework.foundation.mvc.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.mvc.DispatcherServlet;

/**
 * @author buildupchao
 * @date 2019-06-24 20:37
 * @since JDK 1.8
 */
public class TomcatServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);
	
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(8000);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        DispatcherServlet servlet = new DispatcherServlet();
        Tomcat.addServlet(context, Constants.DISPATCHER_SERVLET, servlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/", Constants.DISPATCHER_SERVLET);
        tomcat.getHost().addChild(context);

        Thread awaitThread = new Thread("tomcat-await-thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
        
        LOGGER.info("start tomcat server at port {}", 8000);
    }
}
