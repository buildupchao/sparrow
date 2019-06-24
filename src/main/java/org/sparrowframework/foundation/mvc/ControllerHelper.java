package org.sparrowframework.foundation.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.annotation.RequestMapping;
import org.sparrowframework.foundation.core.ClassHelper;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.mvc.wrapper.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author buildupchao
 * @date 2019-06-24 22:58
 * @since JDK 1.8
 */
public final class ControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> REQUEST_HANDLER_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isEmpty(methods)) {
                    continue;
                }
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String requestPath = requestMapping.value();
                        String methodType = requestMapping.method().name();
                        Request request = new Request(methodType, requestPath);
                        Handler handler = new Handler(controllerClass, method);
                        REQUEST_HANDLER_MAP.put(request, handler);
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_HANDLER_MAP.get(request);
    }
}
