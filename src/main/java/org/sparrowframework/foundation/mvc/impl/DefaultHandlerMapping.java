package org.sparrowframework.foundation.mvc.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sparrowframework.foundation.mvc.ControllerHelper;
import org.sparrowframework.foundation.mvc.HandlerMapping;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.mvc.wrapper.Requester;

/**
 * @author buildupchao
 * @date 2019/06/25 16:11
 * @since JDK 1.8
 */
public class DefaultHandlerMapping implements HandlerMapping {

	/* 
	 * @see org.sparrowframework.foundation.mvc.HandlerMapping#getHandler(java.lang.String, java.lang.String)
	 */
	@Override
	public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
		Handler handler = null;
		
		Map<Requester, Handler> requestHandlerMap = ControllerHelper.getRequestHandlerMap();
		for (Map.Entry<Requester, Handler> requestHandlerEntry : requestHandlerMap.entrySet()) {
			Requester requester = requestHandlerEntry.getKey();
			String methodType = requester.getRequestMethod();
			String requestPath = requester.getRequestPath();
			
			Matcher requestPathMatcher = Pattern.compile(requestPath).matcher(currentRequestPath);
			// matches request method type and request path.
			if (methodType.equalsIgnoreCase(currentRequestMethod) && requestPathMatcher.matches()) {
				handler = requestHandlerEntry.getValue();
				if (handler != null) {
					handler.setRequestPathMatcher(requestPathMatcher);
				}
				break;
			}
		}
		return handler;
	}
}
