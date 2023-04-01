package commons.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guorui1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return getCode() == StatusCode.OK.getCode();
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<T>(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), data);
    }

    public static <T> ResultBean<T> success() {
        return success(null);
    }

    public static <T> ResultBean<T> error(int errorCode, String msg) {
        return new ResultBean<>(errorCode, msg, null);
    }
}
