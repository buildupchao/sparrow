package org.sparrowframework.foundation.mvc.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author buildupchao
 * @date 2019-06-24 23:07
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
public class Handler {
    private Class controllerClass;
    private Method controllerMethod;
}
