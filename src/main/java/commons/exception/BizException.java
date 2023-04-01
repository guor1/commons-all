package commons.exception;

import commons.utils.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author guor
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    /**
     * 默认错误码50000
     */
    private int code = StatusCode.ERROR.getCode();

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
