package org.sparrowframework.foundation.mvc.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author buildupchao
 * @date 2019-06-24 22:57
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
public class Request {
    private String requestMethod;
    private String requestPath;
}
