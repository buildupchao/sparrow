package org.sparrowframework.foundation.core;

import java.util.Map;
import java.util.Properties;

import org.sparrowframework.foundation.constant.Constants;
import org.sparrowframework.foundation.util.PropertiesUtil;

/**
 * @author buildupchao
 * @date 2019/06/24 16:14
 * @since JDK 1.8
 */
public class ConfigHelper {

	private static final Properties properties = PropertiesUtil.loadProperties(Constants.FRAMEWORK_CONFIG_FILE);
	
	public static String getString(String key) {
        return PropertiesUtil.getPropertyValue(properties, key, String.class);
    }

    public static String getString(String key, String defaultValue) {
        return PropertiesUtil.getPropertyValue(properties, key, defaultValue, String.class);
    }

    public static int getInt(String key) {
        return PropertiesUtil.getPropertyValue(properties, key, Integer.class);
    }
    public static int getInt(String key, int defaultValue) {
        return PropertiesUtil.getPropertyValue(properties, key, defaultValue, Integer.class);
    }

    public static boolean getBoolean(String key) {
        return PropertiesUtil.getPropertyValue(properties, key, Boolean.class);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return PropertiesUtil.getPropertyValue(properties, key, defaultValue, Boolean.class);
    }

    public static Map<String, Object> getMap(String prefix) {
        return PropertiesUtil.getPropertyMapByPrefix(properties, prefix);
    }
}
