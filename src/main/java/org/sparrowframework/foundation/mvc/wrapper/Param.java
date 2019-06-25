package org.sparrowframework.foundation.mvc.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.MapUtils;
import org.sparrowframework.foundation.util.ParamUtil;

import java.util.Map;

/**
 * @author buildupchao
 * @date 2019-06-24 23:15
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
public class Param {
    private Map<String, Object> paramMap;

    public boolean isEmpty() {
        return MapUtils.isEmpty(paramMap);
    }

    public long getLong(String name) {
        return ParamUtil.getParamValue(paramMap.get(name), Long.class);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
    
    public Object getParam(String name) {
    	return paramMap.get(name);
    }
}
