package commons.javalin;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 自动启javalin服务器，并结合spring自动注册mapping
 *
 * @author guorui1
 */
public class JavalinServer implements BeanPostProcessor {
    private Javalin javalin;

    @PostConstruct
    public void init() {
        this.javalin = Javalin.create().start(7070);
    }

    @PreDestroy
    public void destroy() {
        this.javalin.stop();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        Path path = AnnotationUtils.findAnnotation(bean.getClass(), Path.class);
        if (path != null && bean instanceof Handler) {
            this.javalin.addHandler(path.handlerType(), path.value(), (Handler) bean);
        }
        return bean;
    }
}
