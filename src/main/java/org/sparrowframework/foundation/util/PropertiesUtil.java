package org.sparrowframework.foundation.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.constant.Constants;

/**
 * @author buildupchao
 * @date 2019/06/24 16:17
 * @since JDK 1.8
 */
public class PropertiesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	private static final Class<?> DEFAULT_CONVERTER_CLASS = String.class;
	
	@SuppressWarnings("serial")
	private static final Map<Class<?>, Function<String, ?>> CONVERTER_MAP = new HashMap<Class<?>, Function<String, ?>>() {{
		put(Boolean.class, Boolean::parseBoolean);
		put(Integer.class, Integer::parseInt);
		put(Long.class, Long::parseLong);
		put(Double.class, Double::parseDouble);
		put(String.class, String::valueOf);
	}};
	
	public static Properties loadProperties(String propertyPath) {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			if (StringUtils.isBlank(propertyPath)) {
				throw new IllegalArgumentException();
			}
			String suffix = Constants.FRAMEWORK_CONFIG_FILE_SUFFIX;
			if (propertyPath.lastIndexOf(suffix) == -1) {
				propertyPath += suffix;
			}
			in = ClassUtil.getClassLoader().getResourceAsStream(propertyPath);
			if (in != null) {
				properties.load(in);
			}
		} catch (Exception ex) {
			LOGGER.error("Load framework config file error!", ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return properties;
	}
	
	public static Map<String, String> loadPropertiesToMap(String propertyPath) {
		Map<String, String> propertyMap = new HashMap<>();
		Properties properties = loadProperties(propertyPath);
		for (String key : properties.stringPropertyNames()) {
			propertyMap.put(key, properties.getProperty(key));
		}
		return propertyMap;
	}
	
	public static Map<String, Object> getPropertyMapByPrefix(Properties properties, String prefix) {
        Map<String, Object> propertyMap = new LinkedHashMap<String, Object>();
        Set<String> keySet = properties.stringPropertyNames();
        if (CollectionUtils.isNotEmpty(keySet)) {
            for (String key : keySet) {
                if (key.startsWith(prefix)) {
                    String value = properties.getProperty(key);
                    propertyMap.put(key, value);
                }
            }
        }
        return propertyMap;
    }
	
	public static <T> T getPropertyValue(Properties properties, String key, Class<T> converterClass) {
		return getPropertyValue(properties, key, null, converterClass);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getPropertyValue(Properties properties, String key, T defaultValue, Class<T> converterClass) {
		T value = defaultValue;
		if (properties.containsKey(key)) {
			String propertyValue = properties.getProperty(key);
			value = (T) CONVERTER_MAP.getOrDefault(converterClass, CONVERTER_MAP.get(DEFAULT_CONVERTER_CLASS)).apply(propertyValue);
		}
		return value;
	}
}
