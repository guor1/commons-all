package commons.javalin;

import io.javalin.http.HandlerType;

import java.lang.annotation.*;

/**
 * 请求路径
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
    /**
     * 路径
     */
    String value();

    /**
     * 请求类型
     */
    HandlerType handlerType() default HandlerType.GET;
}
