package org.sparrowframework.foundation.aop.proxy;

/**
 * @author buildupchao
 * @date 2019/06/25 18:32
 * @since JDK 1.8
 */
public interface Proxy {
	Object doProxy(ProxyChain chain) throws Throwable;
}
