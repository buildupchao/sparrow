package org.sparrowframework.foundation.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sparrowframework.foundation.mvc.wrapper.Handler;

/**
 * @author buildupchao
 * @date 2019/06/25 16:08
 * @since JDK 1.8
 */
public interface HandlerInvoker {
	
	void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception; 
}
