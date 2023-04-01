package commons.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Nonnull;

/**
 * bean初始化拦截器，bean加载完成后，如果是Runnable，则丢到全局线程池中执行
 *
 * @author guor
 */
@Data
public class RunnableBeanPostProcessor implements BeanPostProcessor {

    private ThreadPoolTaskExecutor taskScheduler;

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (bean instanceof Runnable) {
            taskScheduler.submit((Runnable) bean);
        }
        return bean;
    }
}
