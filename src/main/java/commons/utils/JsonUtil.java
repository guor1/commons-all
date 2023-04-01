package commons.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.filter.PropertyFilter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author guorui1
 */
public class JsonUtil {
    /**
     * 对象转map,忽略val为null、空字符串等属性，暂时使用fastjson做转换，可通过反射优化
     */
    public static Map<String, Object> toParamMap(Object obj) {
        return JSON.parseObject(toJsonString(obj));
    }

    /**
     * 对象转json字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, new IgnoreNullPropertyFilter());
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    public static <T> T parseObject(String json, Class<T> klass) {
        return JSON.parseObject(json, klass);
    }

    public static <T> List<T> parseArray(String json, Class<T> klass) {
        return JSON.parseArray(json, klass);
    }

    static class IgnoreNullPropertyFilter implements PropertyFilter {
        @Override
        public boolean apply(Object object, String name, Object value) {
            return !Objects.isNull(value) && !StringUtils.isEmpty(Objects.toString(value));
        }
    }
}
