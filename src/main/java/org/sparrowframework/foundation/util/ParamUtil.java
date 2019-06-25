package org.sparrowframework.foundation.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author buildupchao
 * @date 2019-06-24 23:17
 * @since JDK 1.8
 */
public class ParamUtil {

    private static final Class<?> DEFAULT_CONVERTER_CLASS = String.class;

    @SuppressWarnings("serial")
    private static final Map<Class<?>, Function<String, ?>> CONVERTER_MAP = new HashMap<Class<?>, Function<String, ?>>() {{
        put(Boolean.class, Boolean::parseBoolean);
        put(Integer.class, Integer::parseInt);
        put(Long.class, Long::parseLong);
        put(Double.class, Double::parseDouble);
        put(String.class, String::valueOf);
    }};

    @SuppressWarnings("unchecked")
    public static <T> T getParamValue(Object obj, Class<T> converterClass) {
        T value = null;
        String paramValue = Objects.isNull(obj) ? StringUtils.EMPTY : String.valueOf(obj);
        if (StringUtils.isNoneBlank(paramValue)) {
            value = (T) CONVERTER_MAP.getOrDefault(converterClass, CONVERTER_MAP.get(DEFAULT_CONVERTER_CLASS)).apply(paramValue);
        }
        return value;
    }
    
    public static boolean isInt(Class<?> type) {
    	return type.equals(int.class) || type.equals(Integer.class);
    }
    
    public static boolean isLong(Class<?> type) {
    	return type.equals(long.class) || type.equals(Long.class);
    }
    
    public static boolean isDouble(Class<?> type) {
    	return type.equals(double.class) || type.equals(Double.class);
    }
    
    public static boolean isString(Class<?> type) {
    	return type.equals(String.class);
    }
}
