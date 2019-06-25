package org.sparrowframework.foundation.test.service;

import org.sparrowframework.foundation.tx.annotation.Service;

/**
 * @author buildupchao
 * @date 2019-06-24 20:51
 * @since JDK 1.8
 */
@Service
public class HelloService {

    public String hello(String name) {
        return "hello " + name;
    }
}
