package commons.http;

import lombok.Data;
import okhttp3.Headers;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * HTTP请求结果接口，包含请求URL、状态码、请求响应内容、cookie等信息
 *
 * @author guor
 */
@Data
public class HttpQueryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求原始URL
     */
    private final String baseUrl;

    /**
     * 重定向url
     */
    private final String fetchedUrl;

    /**
     * 响应结果类型
     */
    private final String contentType;

    /**
     * 字符集
     */
    private final String charset;

    /**
     * HTTP状态码
     */
    private final int statusCode;

    /**
     * 请求失败原因
     */
    private final String reasonPhrase;

    /**
     * 响应内容
     */
    private final byte[] content;

    /**
     * 请求响应头
     */
    private final Headers headers;

    /**
     * @param baseUrl      请求原始URL
     * @param contentType  响应结果类型
     * @param statusCode   请求响应状态码
     * @param reasonPhrase 请求失败原因
     * @param content      响应结果
     * @param headers      HTTP响应头部
     */
    public HttpQueryResult(String baseUrl, String fetchedUrl, String contentType, String charset, int statusCode, String reasonPhrase, byte[] content,
                           Headers headers) {
        super();
        this.baseUrl = baseUrl;
        this.fetchedUrl = fetchedUrl;
        this.contentType = contentType;
        this.charset = charset;
        this.content = content;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
    }

    /**
     * @return 以String形式返回响应结果，使用默认字符集
     */
    public String asString() {
        return charset != null ? asString(charset) : asString("UTF-8");
    }

    /**
     * @param charset 字符编码
     * @return 以String形式返回响应结果，使用指定字符集
     */
    public String asString(String charset) {
        return new String(content, Charset.forName(charset));
    }

    /**
     * @param headerName 头名称
     * @return 根据指定headerName返回Header
     */
    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}
