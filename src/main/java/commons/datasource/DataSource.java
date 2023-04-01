package commons.datasource;

import java.lang.annotation.*;

/**
 * 描述: 自定义数据源注解
 *
 * @author guorui1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSource {
    String value();
}
	