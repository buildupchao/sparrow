package org.sparrowframework.foundation.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.sparrowframework.foundation.InstanceFactory;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.util.WebUtil;

/**
 * @author buildupchao
 * @date 2019-06-24 20:41
 * @since JDK 1.8
 */
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 6676267414678587154L;

	private HandlerMapping handlerMapping = InstanceFactory.getHandlerMapping();
	private HandlerInvoker handlerInvoker = InstanceFactory.getHandlerInvoker();
	private HandlerExceptionResolver handlerExceptionResolver = InstanceFactory.getHandlerExceptionResolver();
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);

        String requestMethod = request.getMethod().toUpperCase();
        String requestPath = WebUtil.getRequestPath(request);

        if (requestPath.equals(Constants.PATH_SEPARATOR)) {
        	WebUtil.redirectRequest(Constants.PAGE_HOME, request, response);
        	return;
        }
        if (requestPath.endsWith(Constants.PATH_SEPARATOR)) {
        	requestPath = requestPath.substring(0, requestPath.length() - 1);
        }
        
        Handler handler = handlerMapping.getHandler(requestMethod, requestPath);
        if (handler == null) {
        	WebUtil.sendError(HttpServletResponse.SC_NOT_FOUND, StringUtils.EMPTY, response);
        	return;
        }
        
        try {
			handlerInvoker.invokeHandler(request, response, handler);
		} catch (Exception e) {
			handlerExceptionResolver.resolveHandlerException(request, response, e);
		} finally {
			// other handle
		}
    }

    
}
