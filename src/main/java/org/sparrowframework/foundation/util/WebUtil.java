package org.sparrowframework.foundation.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.constant.Constants.MethodType;
import org.sparrowframework.foundation.mvc.wrapper.Param;

/**
 * @author buildupchao
 * @date 2019/06/25 17:38
 * @since JDK 1.8
 */
public class WebUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);
	
	public static Param fetchParameters(HttpServletRequest request) throws IOException {
        Map<String, Object> paramMap = new HashMap<>();
        
        putFormDataIntoParamMap(request, paramMap);
        putUrlParameterIntoParamMap(request, paramMap);
        
        return new Param(paramMap);
    }
	
	public static Map<String, Object> getRequestParameterMap(HttpServletRequest request) {
		Map<String, Object> paramMap = new LinkedHashMap<>();
		try {
			String methodType = request.getMethod().toUpperCase();
			if (methodType.equals(MethodType.PUT.name()) || methodType.equals(MethodType.DELETE.name())) {
				putUrlParameterIntoParamMap(request, paramMap);
			} else {
				putFormDataIntoParamMap(request, paramMap);
			}
		} catch (Exception ex) {
			LOGGER.error("fetch request parameters failure", ex);
			throw new RuntimeException(ex);
		}
		return paramMap;
	}
	
	private static void putUrlParameterIntoParamMap(HttpServletRequest request, Map<String, Object> paramMap) throws IOException {
		String queryString = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtils.isNoneBlank(queryString)) {
            String[] params = StringUtils.split(queryString, Constants.URL_PARAM_SEPARATOR);
            if (ArrayUtils.isEmpty(params)) {
            	return;
            }
            for (String param : params) {
                String[] array = StringUtils.split(param, Constants.KEY_VALUE_PAIR_SEPARATOR);
                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                    String paramName = array[0];
                    String paramValue = array[1];
                    if (checkParamName(paramName)) {
                    	if (paramMap.containsKey(paramName)) {
                    		paramValue = paramMap.get(paramName) + Constants.PARAMETER_SEPARATOR + paramValue;
                    	}
                    	paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
	}
	
	private static void putFormDataIntoParamMap(HttpServletRequest request, Map<String, Object> paramMap) {
		Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (checkParamName(parameterName)) {
            	String[] parameterValues = request.getParameterValues(parameterName);
            	if (ArrayUtils.isEmpty(parameterValues)) {
            		continue;
            	}
            	if (parameterValues.length == 1) {
            		paramMap.put(parameterName, parameterValues[0]);
            	} else {
            		StringBuilder paramValue = new StringBuilder("");
            		for (int i = 0; i < parameterValues.length; i++) {
            			paramValue.append(parameterValues[i]);
            			if (i != paramValue.length() - 1) {
            				paramValue.append(Constants.PARAMETER_SEPARATOR);
            			}
            		}
            		paramMap.put(parameterName, paramValue.toString());
            	}
            }
        }
	}
	
	private static boolean checkParamName(String paramName) {
		return !"_".equals(paramName);
	}
	
	public static void writeJson(HttpServletResponse response, Object data) {
		try {
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			response.setContentType(Constants.DEFAULT_CONTENT_TYPE);
			PrintWriter writer = response.getWriter();
			writer.write(GsonUtil.gsonString(data));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOGGER.error("write json data into response failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void writeHtml(HttpServletResponse response, Object data) {
		try {
			response.setCharacterEncoding(Constants.CHARSET_UTF_8);
			response.setContentType(Constants.CONTENT_TYPE_HTML);
			PrintWriter writer = response.getWriter();
			writer.write(GsonUtil.gsonString(data));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOGGER.error("write json data into response failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void sendError(int code, String message, HttpServletResponse response) {
		try {
			response.sendError(code, message);
		} catch (IOException e) {
			LOGGER.error("send error code failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + "/" + path);
		} catch (IOException e) {
			LOGGER.error("redirect request failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException | IOException e) {
			LOGGER.error("forward request failure", e);
			throw new RuntimeException(e);
		}
	}
	
	public static String getRequestPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String pathInfo = StringUtils.defaultIfBlank(request.getPathInfo(), StringUtils.EMPTY);
		return servletPath + pathInfo;
	}
	
	private static boolean isAjax(HttpServletRequest request) {
		return request.getHeader("X-Requested-With") != null;
	}
	
	public static void sendRedirectUrl(HttpServletRequest request, String sessionKey) {
		if (!isAjax(request)) {
			String requestPath = getRequestPath(request);
			request.getSession().setAttribute(sessionKey, requestPath);
		}
	}
}
