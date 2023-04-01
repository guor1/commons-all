package commons.http;

/**
 * 基础的网络请求失败异常类，包含请求的URL和原始异常信息
 *
 * @author guor
 */
public class BaseHttpException extends Exception {
    /**
     * 请求URL
     */
    private final String url;

    /**
     * 构造器
     *
     * @param url 异常请求URL
     * @param e   异常堆栈
     */
    public BaseHttpException(String url, Exception e) {
        super(e);
        this.url = url;
    }

    /**
     * 构造器
     *
     * @param url 异常请求URL
     * @param msg 异常信息
     * @param e   异常堆栈
     */
    public BaseHttpException(String url, String msg, Exception e) {
        super(msg, e);
        this.url = url;
    }

    /**
     * 构造器
     *
     * @param url 异常请求URL
     * @param msg 异常信息
     */
    public BaseHttpException(String url, String msg) {
        super(msg);
        this.url = url;
    }

    /**
     * @return 获取异常请求URL
     */
    public String getUrl() {
        return url;
    }
}
