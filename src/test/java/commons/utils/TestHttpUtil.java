package commons.utils;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestHttpUtil {
    @Test
    public void testGet() {
        String url = "http://httpbin.org/get";
        String s = HttpUtil.get(url);
        JSONObject res = JsonUtil.parseObject(s);
        Assertions.assertEquals(url, res.getString("url"));
    }

    @Test
    public void testPost() {
        String url = "http://httpbin.org/post";
        String s = HttpUtil.post(url, StringUtils.EMPTY);
        JSONObject res = JsonUtil.parseObject(s);
        Assertions.assertEquals(url, res.getString("url"));
    }
}
