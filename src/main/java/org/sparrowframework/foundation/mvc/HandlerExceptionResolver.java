package org.sparrowframework.foundation.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author buildupchao
 * @date 2019/06/25 16:09
 * @since JDK 1.8
 */
public interface HandlerExceptionResolver {
	
	void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
