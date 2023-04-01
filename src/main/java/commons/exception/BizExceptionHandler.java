package commons.exception;

import commons.utils.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 异常处理器
 *
 * @author guorui1
 */
@Slf4j
@RestControllerAdvice
public class BizExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public Ret handleBizException(BizException e) {
        return new Ret(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Ret handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Ret.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Ret handleAuthorizationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return Ret.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public Ret handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Ret.error(e.getMessage());
    }

    /**
     * 方法校验失败，返回第一个失败消息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Ret handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return Ret.error(allErrors.get(0).getDefaultMessage());
    }
}
