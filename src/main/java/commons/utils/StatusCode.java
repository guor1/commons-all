package commons.utils;

import lombok.Getter;

/**
 * 服务器状态码枚举常量
 *
 * @author guor
 */
@Getter
public enum StatusCode {
    // 通用状态码
    OK(0, "ok"),
    BAD_REQUEST(400, "BAD REQUEST"),
    /**
     * 未登录或者token过期
     */
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "FORBIDDEN"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "NOT FOUND"),
    ERROR(500, "服务器错误"),
    FEIGN_ERROR(40003, "远程调用失败");

    private final int code;
    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
