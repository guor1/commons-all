package commons.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * 描述：自定义数据源切面
 *
 * @author guorui1
 */
@Slf4j
public class DynamicDataSourceAdvice implements MethodBeforeAdvice {
    @Override
    public void before(@Nonnull Method method, @Nonnull Object[] args, Object target) {
        if (method.isAnnotationPresent(DataSource.class)) {
            DataSource data = method.getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setDataSourceName(data.value());
            log.debug("切换数据源 : [" + data.value() + "]");
        }
    }
}
	