package commons.actuate;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({InfoContributor.class})
public class ThreadPoolInfoContributorAutoConfiguration {

    @Bean
    @ConditionalOnBean
    public InfoContributor infoContributor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        return new ThreadPoolInfoContributor(threadPoolTaskExecutor);
    }
}
