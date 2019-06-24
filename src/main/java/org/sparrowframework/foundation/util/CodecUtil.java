package org.sparrowframework.foundation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.constant.Constants;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author buildupchao
 * @date 2019-06-24 23:28
 * @since JDK 1.8
 */
public class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String decodeURL(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, Constants.DEFAULT_URL_CHARSET);
        } catch (Exception ex) {
            LOGGER.error("decode url failure", ex);
            throw new RuntimeException(ex);
        }
        return target;
    }

    public static String encodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, Constants.DEFAULT_URL_CHARSET);
        } catch (Exception ex) {
            LOGGER.error("encode url failure", ex);
            throw new RuntimeException(ex);
        }
        return target;
    }
}
