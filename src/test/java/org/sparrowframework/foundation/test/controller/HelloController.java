package org.sparrowframework.foundation.test.controller;

import org.sparrowframework.foundation.ioc.annotation.Autowired;
import org.sparrowframework.foundation.mvc.annotation.Controller;
import org.sparrowframework.foundation.mvc.annotation.RequestMapping;
import org.sparrowframework.foundation.test.service.HelloService;

/**
 * @author buildupchao
 * @date 2019-06-24 20:52
 * @since JDK 1.8
 */
@Controller("/ctl")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello")
    public String hello(String name, String gender) {
        return helloService.hello(name) + ":" + gender;
    }
}
