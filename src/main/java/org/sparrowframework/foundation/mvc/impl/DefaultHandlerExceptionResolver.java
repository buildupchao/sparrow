package org.sparrowframework.foundation.mvc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.mvc.HandlerExceptionResolver;
import org.sparrowframework.foundation.util.WebUtil;

/**
 * @author buildupchao
 * @date 2019/06/25 18:56
 * @since JDK 1.8
 */
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);
	
	/* 
	 * @see org.sparrowframework.foundation.mvc.HandlerExceptionResolver#resolveHandlerException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Exception)
	 */
	@Override
	public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		Throwable cause = e.getCause();
		if (cause == null) {
			LOGGER.error(e.getMessage(), e);
			return;
		}
		
		// Other exception check
		// TODO
		WebUtil.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause.getMessage(), response);
	}
}
