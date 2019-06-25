package org.sparrowframework.foundation.mvc.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.sparrowframework.foundation.ioc.BeanHelper;
import org.sparrowframework.foundation.mvc.HandlerInvoker;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.mvc.wrapper.Param;
import org.sparrowframework.foundation.util.ParamUtil;
import org.sparrowframework.foundation.util.ReflectionUtil;
import org.sparrowframework.foundation.util.WebUtil;

/**
 * @author buildupchao
 * @date 2019/06/25 16:42
 * @since JDK 1.8
 */
public class DefaultHandlerInvoker implements HandlerInvoker {

	/* 
	 * @see org.sparrowframework.foundation.mvc.HandlerInvoker#invokeHandler(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.sparrowframework.foundation.mvc.wrapper.Handler)
	 */
	@Override
	public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler)
			throws Exception {
		Class<?> controllerClass = handler.getControllerClass();
		Method controllerMethod = handler.getControllerMethod();
        Object controllerInstance = BeanHelper.getBean(controllerClass);
        
        List<Object> controllerMethodParamList = createControllerMethodParamList(request, handler);
        
        checkParamList(controllerMethod, controllerMethodParamList);
        
        Object controllerMethodResult = invokeControllerMethod(controllerMethod, controllerInstance, controllerMethodParamList);
        
        // TODO...
	}

	/**
	 * @param request
	 * @param handler
	 * @return
	 */
	private List<Object> createControllerMethodParamList(HttpServletRequest request, Handler handler) {
		List<Object> paramList = new ArrayList<>();
		Class<?>[] parameterTypes = handler.getControllerMethod().getParameterTypes();
		paramList.addAll(createPathParamList(handler.getRequestPathMatcher(), parameterTypes));
		
		// will offer upload function in the future
		// TODO
		
		Map<String, Object> requestParameterMap = WebUtil.getRequestParameterMap(request);
		if (MapUtils.isNotEmpty(requestParameterMap)) {
			paramList.add(new Param(requestParameterMap));
		}
		return paramList;
	}
	
	private List<Object> createPathParamList(Matcher requestPathMatcher, Class<?>[] parameterTypes) {
		List<Object> paramList = new ArrayList<>();
		
		for (int i = 1; i <= requestPathMatcher.groupCount(); i++) {
			String param = requestPathMatcher.group(i);
			Class<?> paramType = parameterTypes[i - 1];
			if (ParamUtil.isInt(paramType)) {
				paramList.add(ParamUtil.getParamValue(param, Integer.class));
			} else if (ParamUtil.isLong(paramType)) {
				paramList.add(ParamUtil.getParamValue(param, Long.class));
			} else if (ParamUtil.isDouble(paramType)) {
				paramList.add(ParamUtil.getParamValue(param, Double.class));
			} else if (ParamUtil.isString(paramType)) {
				paramList.add(param);
			}
		}
		return paramList;
	}
	
	private void checkParamList(Method controllerMethod, List<Object> controllerMethodParamList) {
		Class<?>[] parameterTypes = controllerMethod.getParameterTypes();
		if (parameterTypes.length != controllerMethodParamList.size()) {
			String message = String.format("Cannot invoke method because of mismatching number of arguments, origin: %d, real: %d", parameterTypes.length, controllerMethodParamList.size());
			throw new RuntimeException(message);
		}
	}

	private Object invokeControllerMethod(Method controllerMethod, Object controllerInstance, List<Object> controllerMethodParamList) {
		return ReflectionUtil.invokeMethodQuickly(controllerInstance, controllerMethod, controllerMethodParamList.toArray());
	}
}
