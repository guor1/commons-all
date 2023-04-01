package commons.utils;

import cn.zhxu.okhttps.OkHttps;

/**
 * http工具类
 *
 * @author guorui1
 */
public class HttpUtil {
    public static String post(final String url, final String json) {
        return OkHttps.sync(url).setBodyPara(json).post().getBody().toString();
    }

    public static String get(final String url) {
        return OkHttps.sync(url).get().getBody().toString();
    }
}
