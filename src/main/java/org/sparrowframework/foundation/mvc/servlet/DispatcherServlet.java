package org.sparrowframework.foundation.mvc.servlet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.ioc.BeanHelper;
import org.sparrowframework.foundation.mvc.ControllerHelper;
import org.sparrowframework.foundation.mvc.wrapper.Handler;
import org.sparrowframework.foundation.mvc.wrapper.Param;
import org.sparrowframework.foundation.util.CodecUtil;
import org.sparrowframework.foundation.util.GsonUtil;
import org.sparrowframework.foundation.util.ReflectionUtil;
import org.sparrowframework.foundation.util.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author buildupchao
 * @date 2019-06-24 20:41
 * @since JDK 1.8
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        String requestMethod = req.getMethod().toUpperCase();
        String requestPath = req.getPathInfo();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Param param = fetchParameters(req);

            Object result = null;
            Method controllerMethod = handler.getControllerMethod();
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, controllerMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, controllerMethod, param);
            }

            resp.setCharacterEncoding(Constants.DEFAULT_CHARSET);
            resp.setContentType(Constants.DEFAULT_CONTENT_TYPE);
            PrintWriter writer = resp.getWriter();
            String json = GsonUtil.gsonString(result);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private Param fetchParameters(HttpServletRequest request) throws IOException {
        Map<String, Object> paramMap = new HashMap<>();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            paramMap.put(parameterName, parameterValue);
        }
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtils.isNoneBlank(body)) {
            String[] params = StringUtils.split(body, Constants.URL_PARAM_SEPARATOR);
            if (ArrayUtils.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = StringUtils.split(param, Constants.KEY_VALUE_PAIR_SEPARATOR);
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                        String paramName = array[0];
                        String paramValue = array[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
        return new Param(paramMap);
    }
}
