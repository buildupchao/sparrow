package org.sparrowframework.foundation.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.core.ClassHelper;
import org.sparrowframework.foundation.mvc.annotation.RequestMapping;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.mvc.wrapper.Requester;

/**
 * @author buildupchao
 * @date 2019-06-24 22:58
 * @since JDK 1.8
 */
public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Requester, Handler> REQUEST_HANDLER_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
        	Map<Requester, Handler> normalControllerMap = new HashMap<>();
        	Map<Requester, Handler> regexControllerMap = new HashMap<>();
        	
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isEmpty(controllerMethods)) {
                    continue;
                }
                for (Method controllerMethod : controllerMethods) {
                    handleControllerMethod(controllerClass, controllerMethod, normalControllerMap, regexControllerMap);
                }
            }
            REQUEST_HANDLER_MAP.putAll(normalControllerMap);
            REQUEST_HANDLER_MAP.putAll(regexControllerMap);
        }
    }

/*    public static Handler getHandler(String requestMethod, String requestPath) {
        Requester request = new Requester(requestMethod, requestPath);
        return REQUEST_HANDLER_MAP.get(request);
    }*/

	/**
	 * @param controllerClass
	 * @param controllerMethod
	 * @param normalControllerMap
	 * @param regexControllerMap
	 */
	private static void handleControllerMethod(Class<?> controllerClass, Method controllerMethod,
			Map<Requester, Handler> normalControllerMap, Map<Requester, Handler> regexControllerMap) {
		if (controllerMethod.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = controllerMethod.getAnnotation(RequestMapping.class);
            String requestPath = requestMapping.value();
            String methodType = requestMapping.method().name();
            putControllerMap(methodType, requestPath, controllerClass, controllerMethod, normalControllerMap, regexControllerMap);
        }
	}
	
	/**
	 * 
	 * @param requestMethod
	 * @param requestPath
	 * @param controllerClass
	 * @param controllerMethod
	 * @param normalControllerMap
	 * @param regexControllerMap
	 */
	private static void putControllerMap(String methodType, String requestPath, Class<?> controllerClass, 
			Method controllerMethod, Map<Requester, Handler> normalControllerMap, Map<Requester, Handler> regexControllerMap) {
		Requester requester = new Requester(methodType, requestPath);
        Handler handler = new Handler(controllerClass, controllerMethod);
        
        if (requestPath.matches(".+\\{\\w+\\}.*")) {
        	// convert {\w+} to regex (\\w+)
        	requestPath = StringUtils.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
        	regexControllerMap.put(requester, handler);
        } else {
        	normalControllerMap.put(requester, handler);
        }
        
        LOGGER.info("init {}:{}", methodType, requestPath);
	}

	public static Map<Requester, Handler> getRequestHandlerMap() {
		return REQUEST_HANDLER_MAP;
	}
}
