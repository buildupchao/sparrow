package org.sparrowframework.foundation.mvc;

import org.sparrowframework.foundation.mvc.wrapper.Handler;

/**
 * @author buildupchao
 * @date 2019/06/25 16:05
 * @since JDK 1.8
 */
public interface HandlerMapping {
	
	Handler getHandler(String currentRequestMethod, String currentRequestPath);
}
