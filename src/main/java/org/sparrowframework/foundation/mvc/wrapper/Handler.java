package org.sparrowframework.foundation.mvc.wrapper;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

import lombok.Getter;

/**
 * @author buildupchao
 * @date 2019-06-24 23:07
 * @since JDK 1.8
 */
@Getter
public class Handler {
    private Class<?> controllerClass;
    private Method controllerMethod;
    private Matcher requestPathMatcher;
    
    public Handler(Class<?> controllerClass, Method controllerMethod) {
		super();
		this.controllerClass = controllerClass;
		this.controllerMethod = controllerMethod;
	}
    
	public void setRequestPathMatcher(Matcher requestPathMatcher) {
		this.requestPathMatcher = requestPathMatcher;
	}
}
