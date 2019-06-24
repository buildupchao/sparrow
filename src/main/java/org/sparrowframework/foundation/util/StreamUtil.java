package org.sparrowframework.foundation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author buildupchao
 * @date 2019-06-24 23:25
 * @since JDK 1.8
 */
public class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ex) {
            LOGGER.error("get string failure", ex);
            throw new RuntimeException(ex);
        }
        return sb.toString();
    }
}
