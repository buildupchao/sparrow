package org.sparrowframework.foundation.constant;

import org.sparrowframework.foundation.core.ConfigHelper;

/**
 * @author buildupchao
 * @date 2019/06/19 15:57
 * @since JDK 1.8
 */
public interface Constants {
	String FRAMEWORK_CONFIG_FILE = "sparrow.properties";
	String FRAMEWORK_CONFIG_FILE_SUFFIX = ".properties";
	String APP_BASE_PACKAGE = "sparrow.framework.app.base_package";
	String APP_HOME_PAGE = "sparrow.framework.app.home_page";
	String CLASS_SCANNER = "sparrow.framework.custom.class_scanner";
	String HANDLER_MAPPING = "sparrow.framework.custom.handler_mapping";
	String HANDLER_INVOKER = "sparrow.framework.custom.handler_invoker";
	String HANDLER_EXCEPTION_RESOLVER = "sparrow.framework.custom.handler_exception_resolver";
	
	String PAGE_HOME = ConfigHelper.getString(APP_HOME_PAGE, "/index.html");

	String DISPATCHER_SERVLET = "dispatcherServlet";
	enum MethodType {
		POST, GET, PUT, DELETE, HEAD
	}
	String CHARSET_UTF_8 = "UTF-8";
	String DEFAULT_CHARSET = CHARSET_UTF_8;
	String CONTENT_TYPE_JSON = "application/json";
	String CONTENT_TYPE_HTML = "text/html";
	String DEFAULT_CONTENT_TYPE = CONTENT_TYPE_JSON;
	String URL_PARAM_SEPARATOR = "&";
	String KEY_VALUE_PAIR_SEPARATOR = "=";
	String PARAMETER_SEPARATOR = String.valueOf((char) 29);
}
