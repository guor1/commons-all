package commons.http;

import commons.utils.CharsetUtil;
import commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于HTTP协议的网络请求接口
 *
 * @author guor
 */
@Slf4j
public class HttpQuery implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build();

    /**
     * 设置连接超时时间30秒
     */
    private static final int CONNECTION_TIMEOUT = 30;

    /**
     * 设置等待数据超时时间30秒钟
     */
    private static final int READ_TIMEOUT = 30;

    /**
     *
     */
    private static HttpQuery instance;

    /**
     * @return 创建HttpQuery单例
     */
    public static synchronized HttpQuery getInstance() {
        if (instance == null) {
            instance = new HttpQuery();
        }
        return instance;
    }

    /**
     * @return 每次创建一个新的
     */
    public static HttpQuery newInstance() {
        return new HttpQuery();
    }

    /**
     * 使用get方式获取一个页面
     *
     * @param url 待获取页面URL
     * @return 返回获取到的结果
     * @throws BaseHttpException 获取页面失败，抛出异常
     */
    public HttpQueryResult get(String url) throws BaseHttpException {
        return get(url, new ArrayList<>());
    }

    /**
     * 使用get方式获取一个页面，带参数
     *
     * @param url    待获取页面URL
     * @param params 可传递的参数
     * @return 返回获取到的结果
     * @throws BaseHttpException 获取页面失败，抛出异常
     */
    public HttpQueryResult get(String url, List<KeyValue> params) throws BaseHttpException {
        if (null != params && !params.isEmpty()) {
            String encodedParams = encodeParameters(params);
            if (!url.contains("?")) {
                url += "?" + encodedParams;
            } else {
                url += "&" + encodedParams;
            }
        }
        Request.Builder builder = new Request.Builder().url(url).get();
        return doRequest(builder);
    }

    private String encodeParameters(List<KeyValue> params) {
        if (params == null || params.isEmpty()) {
            return StringUtils.EMPTY;
        }
        return params.stream().map(item -> StringUtils.encodeFormFields(item.getKey()) + "=" + StringUtils.encodeFormFields(item.getValue())).collect(Collectors.joining("&"));
    }

    /**
     * 使用post方式获取一个页面，带参数
     *
     * @param url    待获取页面URL
     * @param params 可传递的参数
     * @return 返回获取到的结果
     * @throws BaseHttpException 获取页面失败，抛出异常
     */
    public HttpQueryResult post(String url, List<KeyValue> params) throws BaseHttpException {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null) {
            params.forEach(item -> bodyBuilder.add(item.getKey(), item.getValue()));
        }
        Request.Builder builder = new Request.Builder().url(url).post(bodyBuilder.build());
        return doRequest(builder);

    }

    public HttpQueryResult doRequest(Request.Builder requestBuilder)
            throws BaseHttpException {
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        Request request = requestBuilder.build();
        String url = request.url().toString();
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            String reasonPhrase = response.message();
            String contentType = response.header("Content-Type");
            String contentEncoding = response.header("Content-Encoding");
            try (ResponseBody responseBody = response.body()) {
                if (responseBody == null) {
                    return null;
                }
                byte[] content = responseBody.bytes();
                String charset = detectCharset(contentEncoding, content);
                return new HttpQueryResult(url, url, contentType, charset, statusCode,
                        reasonPhrase, content, response.headers());
            }
        } catch (IOException e) {
            throw new BaseHttpException(url, e);
        }
    }

    /**
     * 检测响应内容charset
     */
    private String detectCharset(String contentEncoding, byte[] content) {
        if (!StringUtils.isEmpty(contentEncoding)
                && Charset.availableCharsets().containsKey(contentEncoding)) {
            return contentEncoding;
        }
        return CharsetUtil.detect(content).toString();
    }
}
